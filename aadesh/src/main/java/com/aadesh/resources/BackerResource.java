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
import com.aadesh.db.BackerDAO;
import com.aadesh.entity.Backer;
import com.aadesh.entity.LoginUser;
import com.aadesh.utils.JsonUtils;
import com.aadesh.utils.Utils;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/backer/{backerid}")
@Produces(MediaType.APPLICATION_JSON)
public class BackerResource {
	private final BackerDAO backerDAO;
	private final AadeshUserDAO userDAO;
	private final Logger logger = Logger.getLogger(BackerResource.class.getName());

	public BackerResource(BackerDAO dao, AadeshUserDAO userDAO) {
		this.backerDAO = dao;
		this.userDAO = userDAO;
	}

	@GET
	@UnitOfWork
	public Response findBacker(@Auth LoginUser authUser, @PathParam("backerid") long backerid) {
		try {
			logger.info(" In findBacker");
			if(authUser != null) {
				return Response.status(Status.OK).entity(JsonUtils.getJson(backerDAO.findById(backerid))).build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Find Backer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Find Backer")).build();
		}
	}

	@DELETE
	@UnitOfWork
	public Response deleteBacker(@Auth LoginUser authUser , @PathParam("backerid") long backerid) {
		try {
			logger.info(" In deleteBacker");
			if(authUser .getId() == 0) {
				backerDAO.delete(backerid);
				return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Backer Deleted Successfully")).build();
			}else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Delete Backer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Delete Backer"))
					.build();
		}
	}

	@PUT
	@UnitOfWork
	public Response updateBacker(@Auth LoginUser authUser , Backer backer) {
		try {
			logger.info(" In updateBacker");
			if(authUser.getId() == 0) {
				
						backerDAO.update(backer);
						return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Backer updated successfully"))
						.build();
					
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Update Backer " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Update Backer"))
					.build();
		}
	}

//	@POST
//	@Path("/logo")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@UnitOfWork
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addLogo(@Auth LabJumpUser authUser ,@PathParam("backerid") long backerid, @FormDataParam("logofile") InputStream is,
//			@FormDataParam("logofile") FormDataContentDisposition fileDetail) {
//		try {
//			logger.info(" In addLogo");
//			if(authUser != null) {
//				if (fileDetail.getFileName() != null) {
//					Backer backerObj = null;
//					byte[] imageByteValue = IOUtils.toByteArray(is);
//					backerObj = backerDAO.findById(backerid);
//					backerObj.setBackerLogo(imageByteValue);
//					return Response.status(Status.OK).entity(JsonUtils.getJson(backerDAO.update(backerObj))).build();
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

	

//	@PUT
//	@Path("/reset")
//	@UnitOfWork
//	public Response resetBacker(@Auth LabJumpUser authUser ,@PathParam("backerid") long backerid) {
//		try {
//			logger.info(" In resetBacker");
//			if(authUser.getId() == 0) {
//				Backer backerObj = backerDAO.findById(backerid);
//				List<LabJumpUser> labjumpUserList = userDAO.findByEmail(backerObj.getBackerEmail());
//				for (LabJumpUser labJumpUser : labjumpUserList) {
//					labJumpUser.setPassword(Utils.encodeBase64("admin"));
//					userDAO.update(labJumpUser);
//				}
//				return Response.status(Status.OK)
//					.entity(JsonUtils.getSuccessJson(
//							"Password reset successfully to - \"admin\""))
//					.build();
//			} else {
//				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
//			}
//		} catch (Exception e) {
//			logger.severe("Unable to Reset Backer Password" + e);
//			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Reset Backer Password")).build();
//		}
//	}
}
