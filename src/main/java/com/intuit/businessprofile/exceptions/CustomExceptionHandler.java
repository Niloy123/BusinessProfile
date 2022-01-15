package com.intuit.businessprofile.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<String> errorMessage = new ArrayList<>();
		errorMessage.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("0001", errorMessage);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}
}
