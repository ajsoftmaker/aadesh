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
import com.aadesh.db.TenantDAO;
import com.aadesh.entity.LoginUser;
import com.aadesh.entity.Tenant;
import com.aadesh.utils.JsonUtils;
import com.aadesh.utils.Utils;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/tenant/{tenantid}")
@Produces(MediaType.APPLICATION_JSON)
public class TenantResource {
	private final TenantDAO tenantDAO;
	private final AadeshUserDAO userDAO;
	private final Logger logger = Logger.getLogger(TenantResource.class.getName());

	public TenantResource(TenantDAO dao, AadeshUserDAO userDAO) {
		this.tenantDAO = dao;
		this.userDAO = userDAO;
	}

	@GET
	@UnitOfWork
	public Response findTenant(@Auth LoginUser authUser, @PathParam("tenantid") long tenantid) {
		try {
			logger.info(" In find Team Member");
			if(authUser != null) {
				return Response.status(Status.OK).entity(JsonUtils.getJson(tenantDAO.findById(tenantid))).build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Find Team Member " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Find Team Member")).build();
		}
	}

	@DELETE
	@UnitOfWork
	public Response deleteTenant(@Auth LoginUser authUser , @PathParam("tenantid") long tenantid) {
		try {
			logger.info(" In delete Team Member");
			if(authUser .getId() == 0) {
				tenantDAO.delete(tenantid);
				return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Team Member Deleted Successfully")).build();
			}else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Delete Team Member " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Delete Team Member"))
					.build();
		}
	}

	@PUT
	@UnitOfWork
	public Response updateTenant(@Auth LoginUser authUser , Tenant tenant) {
		try {
			logger.info(" In update Team Member");
			if(authUser.getId() == 0) {
				LoginUser userObj = userDAO.findByEmailWithRole(tenant.getTenantEmail(), LoginUser.TENANTADMIN);
			// if there is change in the status (enabled/disabled)
					if (tenant.getTenantStatus() != userObj.getStatus()) {
						tenantDAO.update(tenant);
				// change the login status in labjumpusers for this tenant admin
						userObj.setStatus(tenant.getTenantStatus());
						userDAO.update(userObj);
				// also change status of all the students for this tenant
						List<LoginUser> listOfStudents = userDAO.findStudentsByTenantID(tenant.getId());
						for (int i = 0; i < listOfStudents.size(); i++) {
					// first update the labjumpuser entry for this student
							LoginUser user = listOfStudents.get(i);
							user.setStatus(tenant.getTenantStatus());
							userDAO.update(user);
					
						}
						return Response.status(Status.OK).entity(JsonUtils.getSuccessJson("Team Member updated successfully"))
						.build();
					} else {
						logger.severe("Unable to Update Team Member");
						return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Update Team Member"))
						.build();
					}
			} else {
				return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Authorization Failed")).build();
			}
		} catch (Exception e) {
			logger.severe("Unable to Update Team Member " + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Update Team Member"))
					.build();
		}
	}

//	@POST
//	@Path("/logo")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@UnitOfWork
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response addLogo(@Auth LabJumpUser authUser ,@PathParam("tenantid") long tenantid, @FormDataParam("logofile") InputStream is,
//			@FormDataParam("logofile") FormDataContentDisposition fileDetail) {
//		try {
//			logger.info(" In addLogo");
//			if(authUser != null) {
//				if (fileDetail.getFileName() != null) {
//					Tenant tenantObj = null;
//					byte[] imageByteValue = IOUtils.toByteArray(is);
//					tenantObj = tenantDAO.findById(tenantid);
//					tenantObj.setTenantLogo(imageByteValue);
//					return Response.status(Status.OK).entity(JsonUtils.getJson(tenantDAO.update(tenantObj))).build();
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
	public Response resetTenant(@Auth LoginUser authUser ,@PathParam("tenantid") long tenantid) {
		try {
			logger.info(" In reset Team Member");
			if(authUser.getId() == 0) {
				Tenant tenantObj = tenantDAO.findById(tenantid);
				List<LoginUser> labjumpUserList = userDAO.findByEmail(tenantObj.getTenantEmail());
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
			logger.severe("Unable to Reset Team Member Password" + e);
			return Response.status(Status.BAD_REQUEST).entity(JsonUtils.getErrorJson("Unable to Reset Team Member Password")).build();
		}
	}
}
