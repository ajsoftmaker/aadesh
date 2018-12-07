package com.aadesh.db;

import java.util.List;
import org.hibernate.SessionFactory;

import com.aadesh.entity.LoginUser;

import io.dropwizard.hibernate.AbstractDAO;

public class AadeshUserDAO extends AbstractDAO<LoginUser> {

	public AadeshUserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public LoginUser create(LoginUser user) {
		return persist(user);
	}

	public List<LoginUser> findByLoginID(String userloginID) {
		return list(namedQuery("com.aadesh.entity.LoginUser.findByuserloginID")
				.setParameter("userloginID", userloginID));
	}

	public LoginUser update(LoginUser user) {
		return persist(user);
	}

	public List<LoginUser> findByEmail(String email) {
		return list(namedQuery("com.aadesh.entity.LoginUser.findByEmail")
				.setParameter("email", email));
	}

	public LoginUser findByEmailWithRole(String email, String role) {
		return uniqueResult(namedQuery("com.aadesh.entity.LoginUser.findByEmailWithRole")
				.setParameter("email", email).setParameter("userRole", role));

	}

	public List<LoginUser> findStudentsByTenantID(long tenantID) {
		return list(namedQuery("com.aadesh.entity.LoginUser.findStudentsByTenantID")
				.setParameter("tenant_id", tenantID)
				.setParameter("userRole", LoginUser.STUDENT));
	}

	public List<LoginUser> findUserByLoginIdAndPassword(String userloginID, String password) {
		return list(namedQuery("com.aadesh.entity.LoginUser.findUserByLoginIdAndPassword")
				.setParameter("userloginID", userloginID)
				.setParameter("password",password));
	}
}
