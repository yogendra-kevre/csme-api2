package com.csme.csmeapi.exception;

import java.util.List;
import java.util.stream.Collectors;

public class CustomBodyValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	List<String> constraintViolations;
	
	public CustomBodyValidationException(List<String> constraintViolations) {
		
		super(constraintViolations.stream().collect(Collectors.joining()));
		this.constraintViolations = constraintViolations;
	}
	
	public List<String> getConstraintViolations() {
		return constraintViolations;
	}

}
