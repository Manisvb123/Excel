package com.merit.tlgapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.merit.tlgapp.exception.TLGException;
import com.merit.tlgapp.model.DashboardList;

import com.merit.tlgapp.model.generic.GenericRequest;
import com.merit.tlgapp.services.TLGServices;

@Path("TLGDashboard")

public class TLGDashboard {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDashboardList(GenericRequest request) {
		DashboardList dbList = null;
		try {
			dbList = TLGServices.getDashbordList(request);
			return Response.status(Response.Status.OK).entity(dbList).build();
		} catch (TLGException tlge) {
			return tlge.toResponse(tlge);
		}
	}
}