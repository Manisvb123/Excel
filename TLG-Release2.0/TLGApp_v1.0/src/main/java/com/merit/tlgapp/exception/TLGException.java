package com.merit.tlgapp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TLGException extends Exception
		implements ExceptionMapper<TLGException> {
	private static final long serialVersionUID = 1L;

	public TLGException() {
		super("{\"Error\":\"TLGException\"}");
	}

	public TLGException(String error) {
		super("{\"Error\":\"TLGException - " + error + "\"}");
	}

	@Override
	public Response toResponse(TLGException exception) {
		return Response.status(500).entity(exception.toString().replace("\"","")).type("application/json").build();
	}
}
