package com.aadesh;

import java.util.Map;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import com.aadesh.entity.*;
import com.aadesh.health.TemplateHealthCheck;
import com.aadesh.resources.*;
import com.aadesh.auth.*;
import com.aadesh.db.*;

public class AadeshWebApplication extends Application<AadeshWebConfiguration> {
	private final HibernateBundle<AadeshWebConfiguration> hibernateBundle = 
            new HibernateBundle<AadeshWebConfiguration>(Tenant.class, Volunteer.class, LoginUser.class, 
            		Backer.class){
           
				@Override
				public PooledDataSourceFactory getDataSourceFactory(AadeshWebConfiguration configuration) {
					return configuration.getDataSourceFactory();
				}
            };
            
    public static void main(String[] args) throws Exception {
        new AadeshWebApplication().run(args);
    }

   
    @Override
    public String getName() {
        return "Aadesh";
    }

    @Override
    public void initialize(Bootstrap<AadeshWebConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html", "WebApp"));
        bootstrap.addBundle(new MigrationsBundle<AadeshWebConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AadeshWebConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle<AadeshWebConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(AadeshWebConfiguration configuration) {
                return configuration.getViewRendererConfiguration();
            }
        });
    }

    @Override
    public void run(AadeshWebConfiguration configuration, Environment environment) {
    	ApplicationContext.init(configuration);
    	((DefaultServerFactory) configuration.getServerFactory()).setJerseyRootPath("/api/*");
    	environment.jersey().register(RolesAllowedDynamicFeature.class);
    	environment.healthChecks().register("template", new TemplateHealthCheck());
    	
    	CachingAuthenticator<BasicCredentials, LoginUser> cachingAuthenticator = new CachingAuthenticator<>(
    			environment.metrics(),
    			new AadeshAuthenticator(hibernateBundle.getSessionFactory()),
    			configuration.getAuthenticationCachePolicy());
    			environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<LoginUser>()
    			.setAuthenticator(cachingAuthenticator).setRealm("BootInfoTech").buildAuthFilter()));
    			environment.jersey().register(new AuthValueFactoryProvider.Binder<>(LoginUser.class));
    	
    	registerLabJumpResources(configuration, environment);
      
    }

	private void registerLabJumpResources(AadeshWebConfiguration configuration,Environment environment) {
		final AadeshUserDAO ljuserDAO = new AadeshUserDAO(hibernateBundle.getSessionFactory());
		final TenantDAO tenantDAO = new TenantDAO(hibernateBundle.getSessionFactory());
		final LoginResource loginRes = new LoginResource(configuration.getAdminuser(), configuration.getAdminpwd(), ljuserDAO);
		environment.jersey().register(loginRes);
		
		final TenantResource tenantResource = new TenantResource(tenantDAO, ljuserDAO);
		environment.jersey().register(tenantResource);
		final TenantsResource tenantsResource = new TenantsResource(tenantDAO,ljuserDAO);
		environment.jersey().register(tenantsResource);
		
		final VolunteerDAO volunteerDAO = new VolunteerDAO(hibernateBundle.getSessionFactory());
		final VolunteerResource volunteerResource = new VolunteerResource(volunteerDAO, ljuserDAO);
		environment.jersey().register(volunteerResource);
		final VolunteersResource volunteersResource = new VolunteersResource(volunteerDAO,ljuserDAO);
		environment.jersey().register(volunteersResource);
		
		final BackerDAO backerDAO = new BackerDAO(hibernateBundle.getSessionFactory());
		final BackerResource backerResource = new BackerResource(backerDAO, ljuserDAO);
		environment.jersey().register(backerResource);
		final BackersResource backersResource = new BackersResource(backerDAO,ljuserDAO);
		environment.jersey().register(backersResource);
		
		final AadeshUsersResource labJumpUserResource = new AadeshUsersResource(ljuserDAO, tenantDAO);
		environment.jersey().register(labJumpUserResource);
		
	}
}
