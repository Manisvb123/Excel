package com.merit.tlgapp.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.merit.tlgapp.exception.TLGException;
import com.merit.tlgapp.model.Questionnaire;
import com.merit.tlgapp.model.generic.GenericRequest;
import com.merit.tlgapp.model.generic.GenericResponse;
import com.merit.tlgapp.services.TLGServices;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("TLGQuestionnaire")
public class TLGQuestionnaire {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Path("getQuestionnaire")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuestionnaire(GenericRequest request) {
		Questionnaire szQuestionnaire = null;
		try {
			szQuestionnaire = TLGServices.getQuestionnaire(request);
			return Response.status(Response.Status.OK).entity(szQuestionnaire).build();
		} catch (TLGException tlge) {
			return tlge.toResponse(tlge);
		}
	}

	@POST
	@Path("submitQuestionnaire")
	@Produces(MediaType.APPLICATION_JSON)
	public Response submitQuestionnaire(GenericRequest request) {
		GenericResponse genResp = null;
		try {
			genResp = TLGServices.submitQuestionnaire(request);
			return Response.status(Response.Status.OK).entity(genResp).build();
		} catch (TLGException tlge) {
			return tlge.toResponse(tlge);
		}
	}
}
