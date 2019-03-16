package com.aws.deploy.awsuserdetails.customexception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomRepositoryException extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		ExceptionResponseObject respone = new ExceptionResponseObject(ex.getMessage(),
				request.getDescription(false), new Date());
		return new ResponseEntity<Object>(respone, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {
		ExceptionResponseObject respone = new ExceptionResponseObject(ex.getMessage(),
				request.getDescription(false), new Date());
		return new ResponseEntity<Object>(respone, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponseObject respone = new ExceptionResponseObject(ex.getMessage(),
				ex.getBindingResult().toString(), new Date());
		return new ResponseEntity<Object>(respone, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
