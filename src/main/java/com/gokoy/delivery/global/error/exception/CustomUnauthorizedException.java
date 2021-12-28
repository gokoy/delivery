package com.gokoy.delivery.global.error.exception;

public class CustomUnauthorizedException extends CustomException{
    public CustomUnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public CustomUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CustomUnauthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
