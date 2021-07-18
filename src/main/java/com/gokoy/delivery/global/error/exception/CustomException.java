package com.gokoy.delivery.global.error.exception;

public class CustomException extends RuntimeException {
	private ErrorCode errorCode;

	public CustomException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public CustomException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public CustomException(String message) {
		super(message);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
