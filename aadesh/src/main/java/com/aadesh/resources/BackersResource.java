package com.aadesh.resources;

import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.aadesh.db.AadeshUserDAO;
import com.aadesh.db.BackerDAO;
import com.aadesh.entity.Backer;
import com.aadesh.entity.LoginUser;
import com.aadesh.utils.JsonUtils;
import com.aadesh.utils.MailUtils;
import com.aadesh.utils.SendMail;
import com.aadesh.utils.Utils;
import com.google.gson.JsonObject;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/backers")
@Produces(MediaType.APPLICATION_JSON)
public class BackersResource {
	private final BackerDAO backerDAO;
	private final AadeshUserDAO userDAO;
	private final Logger logger = Logger.getLogger(BackersResource.class.getName());

	public BackersResource(BackerDAO dao, AadeshUserDAO userDAO) {
		this.backerDAO = dao;
		this.userDAO = userDAO;

	}

	@GET
	@UnitOfWork
	public Response listBackers(@Auth LoginUser authUser) {
		try {
			logger.info(" In listBackers");
			return Response.status(Status.OK).entity(JsonUtils.getJson(backerDAO.findAll())).build();
		} catch (Exception e) {
			logger.severe("Unable to Find List of Backers " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Find List of Backers"))
					.build();
		}
	}

	@POST
	@UnitOfWork
	public Response createBacker(@Auth LoginUser auth, Backer backer) {
		Response response = null;
		try {
			logger.info(" In createBacker");
			Backer backerObject = backerDAO.findByEmail(backer.getBackerEmail());
			if (backerObject == null) {
				response = backerDAO.create(backer);
				return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Backer Created Successfully")).build();
			} else {
				logger.severe("Email already Registered. Use different Email ID");
				return Response.status(Status.BAD_REQUEST)
						.entity(JsonUtils.getErrorJson(" Email already Registered. Use different Email ID")).build();
			}

		} catch (Exception e) {
			logger.severe("Unable to Create Backer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Create Backer"))
					.build();
		}
	}
}
