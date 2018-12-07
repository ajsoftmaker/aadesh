package com.aadesh.resources;

import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.aadesh.db.AadeshUserDAO;
import com.aadesh.db.VolunteerDAO;
import com.aadesh.entity.LoginUser;
import com.aadesh.entity.Volunteer;
import com.aadesh.utils.JsonUtils;
import com.aadesh.utils.Utils;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/volunteer/{volunteerid}")
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerResource {
	private final VolunteerDAO volunteerDAO;
	private final AadeshUserDAO userDAO;
	private final Logger logger = Logger.getLogger(VolunteerResource.class.getName());

	public VolunteerResource(VolunteerDAO dao, AadeshUserDAO userDAO) {
		this.volunteerDAO = dao;
		this.userDAO = userDAO;
	}

	@GET
	@UnitOfWork
	public Response findVolunteer(@Auth LoginUser authUser, @PathParam("volunteerid") long volunteerid) {
		try {
			logger.info(" In findVolunteer");
			if(authUser != null) {
				return Response.status(Status.OK).entity(JsonUtils.getJson(volunteerDAO.findById(volunteerid))).build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Find Volunteer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Find Volunteer")).build();
		}
	}

	@DELETE
	@UnitOfWork
	public Response deleteVolunteer(@Auth LoginUser authUser , @PathParam("volunteerid") long volunteerid) {
		try {
			logger.info(" In deleteVolunteer");
			if(authUser .getId() == 0) {
				volunteerDAO.delete(volunteerid);
				return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Volunteer Deleted Successfully")).build();
			}else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Delete Volunteer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Delete Volunteer"))
					.build();
		}
	}

	@PUT
	@UnitOfWork
	public Response updateVolunteer(@Auth LoginUser authUser , Volunteer volunteer) {
		try {
			logger.info(" In updateVolunteer");
			if(authUser.getId() == 0) {
				
						volunteerDAO.update(volunteer);
						return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Volunteer updated successfully"))
						.build();
					
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Update Volunteer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Update Volunteer"))
					.build();
		}
	}

//	@POST
//	@Path("/logo")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@UnitOfWork
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addLogo(@Auth LabJumpUser authUser ,@PathParam("volunteerid") long volunteerid, @FormDataParam("logofile") InputStream is,
//			@FormDataParam("logofile") FormDataContentDisposition fileDetail) {
//		try {
//			logger.info(" In addLogo");
//			if(authUser != null) {
//				if (fileDetail.getFileName() != null) {
//					Volunteer volunteerObj = null;
//					byte[] imageByteValue = IOUtils.toByteArray(is);
//					volunteerObj = volunteerDAO.findById(volunteerid);
//					volunteerObj.setVolunteerLogo(imageByteValue);
//					return Response.status(Status.OK).entity(JsonUtils.getJson(volunteerDAO.update(volunteerObj))).build();
//				} else {
//					logger.severe("Unable to Add Logo");
//					return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Add Logo")).build();
//				}
//			} else {
//				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed , Unable to Add Logo")).build();
//			}
//		} catch (Exception e) {
//			logger.severe("Unable to Add Logo " + e);
//			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Add Logo")).build();
//		}
//	}

	

	@PUT
	@Path("/reset")
	@UnitOfWork
	public Response resetVolunteer(@Auth LoginUser authUser ,@PathParam("volunteerid") long volunteerid) {
		try {
			logger.info(" In resetVolunteer");
			if(authUser.getId() == 0) {
				Volunteer volunteerObj = volunteerDAO.findById(volunteerid);
				List<LoginUser> labjumpUserList = userDAO.findByEmail(volunteerObj.getVolunteerEmail());
				for (LoginUser loginUser : labjumpUserList) {
					loginUser.setPassword(Utils.encodeBase64("admin"));
					userDAO.update(loginUser);
				}
				return Response.status(Status.OK)
					.entity(JsonUtils.getSuccessJson(
							"Password reset successfully to - \"admin\""))
					.build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Reset Volunteer Password" + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Reset Volunteer Password")).build();
		}
	}
}
