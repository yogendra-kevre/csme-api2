package com.csme.csmeapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = CustomValidationException.class)
	public ResponseEntity<ErrorMessage> handleBaseException(Exception ex) {

		CustomValidationException validationException = (CustomValidationException) ex;
		ErrorCode errorCode = validationException.getErrorCode();
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(errorCode.getErrorCode());
		errorMessage.setMessage(errorCode.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = CustomBodyValidationException.class)
	public ResponseEntity<BodyErrorMessage> handleRequestBodyException(Exception ex) {

		CustomBodyValidationException validationException = (CustomBodyValidationException) ex;
		
		BodyErrorMessage errorMessage = new BodyErrorMessage(400,validationException.getConstraintViolations());
	
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}