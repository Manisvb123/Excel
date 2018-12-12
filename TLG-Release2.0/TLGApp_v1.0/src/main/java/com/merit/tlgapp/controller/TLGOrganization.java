package com.merit.tlgapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.merit.tlgapp.exception.TLGException;

import com.merit.tlgapp.model.OrganizationRequest;
import com.merit.tlgapp.model.generic.GenericResponse;
import com.merit.tlgapp.services.TLGServices;
@Path("TLGOrganization")
public class TLGOrganization {
	@POST
	@Path("registerOrg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerOrg(OrganizationRequest request) {
		GenericResponse orgResp = null;
			try {
				orgResp = TLGServices.registerOrg(request);
				 return Response.status(Response.Status.OK).entity(orgResp).build();
			} catch (TLGException tlge) {
				return tlge.toResponse(tlge);
			}
	}
	
	@POST
	@Path("updateOrg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOrg(OrganizationRequest request) {
		GenericResponse orgResp = null;
			try {
				orgResp = TLGServices.updateOrg(request);
				 return Response.status(Response.Status.OK).entity(orgResp).build();
			} catch (TLGException tlge) {
				return tlge.toResponse(tlge);
			}
	}
}
