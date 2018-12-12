package com.merit.tlgapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.merit.tlgapp.exception.TLGException;
import com.merit.tlgapp.model.Page;
import com.merit.tlgapp.model.PageDetails;
import com.merit.tlgapp.model.generic.GenericRequest;
import com.merit.tlgapp.model.generic.GenericResponse;
import com.merit.tlgapp.services.TLGServices;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("TLGPage")
public class TLGPage {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Path("getPageDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPageDetails(GenericRequest request) {
		PageDetails szPageDet = null;
		try {
			szPageDet = TLGServices.getPageDetails(request);
			return Response.status(Response.Status.OK).entity(szPageDet).build();
		} catch (TLGException tlge) {
			return tlge.toResponse(tlge);
		}
	}

	@POST
	@Path("getQuestionnairePage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPage(GenericRequest request) {
		Page szPage = null;
		try {
			szPage = TLGServices.getQuestionnairePage(request);
			return Response.status(Response.Status.OK).entity(szPage).build();
		} catch (TLGException tlge) {
			return tlge.toResponse(tlge);
		}
	}

	@POST
	@Path("savePage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePage(Page page) {
		GenericResponse genResp = null;
		try {
			genResp = TLGServices.savePage(page);
			return Response.status(Response.Status.OK).entity(genResp).build();
		} catch (TLGException tlge) {
			return tlge.toResponse(tlge);
		}
	}
}
