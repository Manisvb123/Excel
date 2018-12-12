package com.merit.tlgapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.merit.tlgapp.exception.MissingRequestParameterException;
import com.merit.tlgapp.exception.TLGException;
import com.merit.tlgapp.model.LoginRequest;
import com.merit.tlgapp.model.LoginResponse;
import com.merit.tlgapp.services.TLGServices;

@Path("TLGLogin")
public class TLGLogin {
	
	@POST
	@Path("getUserDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserDetail(LoginRequest request) {
		LoginResponse loginResp = null;
			try {
				loginResp = TLGServices.getUserDetail(request);
				 return Response.status(Response.Status.OK).entity(loginResp).build();
			} catch (MissingRequestParameterException e) {
				return e.toResponse(e);
			} catch (TLGException e) {
				return e.toResponse(e);
			}
	}

}
