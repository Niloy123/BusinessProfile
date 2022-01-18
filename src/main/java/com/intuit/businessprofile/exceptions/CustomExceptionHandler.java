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

	@ExceptionHandler(ValidationException.class)
	public final ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
		List<String> errorMessage = new ArrayList<>();
		errorMessage.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("0002", errorMessage);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		List<String> errorMessage = new ArrayList<>();
		errorMessage.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("0003", errorMessage);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
}
