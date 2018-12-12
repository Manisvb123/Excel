package com.merit.tlgapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.merit.tlgapp.exception.TLGException;

import com.merit.tlgapp.model.ProjectRequest;
import com.merit.tlgapp.model.Project;
import com.merit.tlgapp.services.TLGServices;

@Path("TLGProject")
public class TLGProject {
	@POST
	@Path("getProjectList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectList(ProjectRequest request) {
		Project ProjectResp = null;
			try {
				ProjectResp = TLGServices.getProjectList(request);
				 return Response.status(Response.Status.OK).entity(ProjectResp).build();
			} catch (TLGException tlge) {
				return tlge.toResponse(tlge);
			}
	}
}
