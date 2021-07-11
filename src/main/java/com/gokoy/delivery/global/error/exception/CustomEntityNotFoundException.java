package com.gokoy.delivery.global.error.exception;

public class CustomEntityNotFoundException extends CustomException {
	public CustomEntityNotFoundException(ErrorCode errorCode) {
		super(errorCode.getMessage(), errorCode);
	}

	public CustomEntityNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}
}
