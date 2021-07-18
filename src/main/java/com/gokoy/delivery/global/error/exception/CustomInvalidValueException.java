package com.gokoy.delivery.global.error.exception;

public class CustomInvalidValueException extends CustomException {
	public CustomInvalidValueException(ErrorCode errorCode) {
		super(errorCode.getMessage(), errorCode);
	}

	public CustomInvalidValueException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}
}
