package com.aadesh.auth;

import org.hibernate.SessionFactory;

import com.aadesh.entity.LoginUser;

import java.util.Optional;
import java.util.logging.Logger;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class AadeshAuthenticator implements Authenticator<BasicCredentials, LoginUser> {
	private SessionFactory factory = null;
	private final Logger logger = Logger.getLogger(AadeshAuthenticator.class.getName());

	public AadeshAuthenticator(SessionFactory factory) {
		this.factory = factory;
		
	}

	@Override
	public Optional<LoginUser> authenticate(BasicCredentials credentials) throws AuthenticationException {

		try {
			String token = credentials.getUsername();
			LoginUser userObj = JwtToken.decryptPayload(token);
			return Optional.of(userObj);

		} catch (Exception e) {
			logger.info("Unable to authenticate user :" + e);
		}
		return Optional.empty();
	}
}
