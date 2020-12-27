package com.csme.csmeapi.exception;

import java.util.List;

public class BodyErrorMessage {

	
	private int errorCode;
	private List<String> message;
	
	public BodyErrorMessage(int errorCode, List<String> message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}


}