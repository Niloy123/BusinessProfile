package com.intuit.businessprofile.exceptions;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
	// General error message about nature of error
	private String errorCode;

	// Specific errors in API request processing
	private List<String> errorMessage;

	public ErrorResponse(String errorCode, List<String> errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
