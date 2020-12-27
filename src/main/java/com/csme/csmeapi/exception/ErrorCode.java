package com.csme.csmeapi.exception;

public enum ErrorCode {
	
	CSME_CE_1001(1001, "Oops!!! Something went wrong. Please contact the administrator.");

	private final int code;
	private final String message;

	ErrorCode(int errorCode, String message) {
		this.code = errorCode;
		this.message = message;
	}

	public int getErrorCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}