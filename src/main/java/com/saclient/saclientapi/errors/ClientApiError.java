/**
 * 
 */
package com.saclient.saclientapi.errors;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @author Krishna
 *
 */
public class ClientApiError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus status;
    private String message;
    private List<String> errors;
	
	public ClientApiError() {
		super();
	}

	public ClientApiError(String message) {
		super(message);
		this.message = message;
	}
	
	public ClientApiError(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ClientApiError(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		this.errors = Arrays.asList(error);
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
