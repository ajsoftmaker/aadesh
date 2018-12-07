package com.aadesh.auth;

import com.aadesh.entity.LoginUser;

import io.dropwizard.auth.Authorizer;

public class AadeshAuthorizer implements Authorizer<LoginUser> {

    @Override
    public boolean authorize(LoginUser user, String role) {
    	if (user.isUserInRole(role))
			return true;

		return false;
    }
}
