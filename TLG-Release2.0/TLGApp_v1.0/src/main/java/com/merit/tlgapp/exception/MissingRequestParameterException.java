package com.merit.tlgapp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MissingRequestParameterException extends Exception
		implements ExceptionMapper<MissingRequestParameterException> {
	private static final long serialVersionUID = 1L;

	public MissingRequestParameterException() {
		super("{\"Error\":\"MissingRequestParameterException\"}");
	}

	public MissingRequestParameterException(String paramName) {
		super("{\"Error\":\"MissingRequestParameterException - " + paramName + "\"}");
	}

	@Override
	public Response toResponse(MissingRequestParameterException exception) {
		return Response.status(500).entity(exception.getMessage()).type("application/json").build();
	}
}
