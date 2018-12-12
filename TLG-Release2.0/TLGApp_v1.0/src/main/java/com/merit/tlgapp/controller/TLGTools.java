package com.merit.tlgapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.merit.tlgapp.exception.TLGException;
import com.merit.tlgapp.model.ToolsRequest;
import com.merit.tlgapp.model.Tools;
import com.merit.tlgapp.services.TLGServices;
@Path("TLGTools")
public class TLGTools {
	@POST
	@Path("getToolsList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToolsList(ToolsRequest request) {
		Tools ToolsResp = null;
			try {
				ToolsResp = TLGServices.getToolList(request);
				 return Response.status(Response.Status.OK).entity(ToolsResp).build();
			} catch (TLGException tlge) {
				return tlge.toResponse(tlge);
			}
	}
}
