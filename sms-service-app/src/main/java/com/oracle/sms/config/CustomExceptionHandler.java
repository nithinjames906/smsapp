package com.oracle.sms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.oracle.sms.constants.ResponseStatus;
import com.oracle.sms.dto.ApiResponse;
import com.oracle.sms.exceptions.SMSException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiResponse> handleUserNotFoundException(Exception ex, WebRequest request) {
		logger.debug(ex.getMessage());
		String message = ex instanceof SMSException ? ex.getMessage() : "An unknown error has occured.";
		ex.printStackTrace();
		ApiResponse error = new ApiResponse(ResponseStatus.FAILURE.getStatus(), message);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.debug(ex.getMessage());
		return new ResponseEntity<>(new ApiResponse(ResponseStatus.FAILURE.getStatus(), "REQUEST PARAMS NOT MATCH!"),
				HttpStatus.BAD_REQUEST);
	}

}
