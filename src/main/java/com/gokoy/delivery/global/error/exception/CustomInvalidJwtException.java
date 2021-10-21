package com.gokoy.delivery.global.error.exception;

public class CustomInvalidJwtException extends CustomException {
    public CustomInvalidJwtException() {
        super(ErrorCode.INVALID_JWT);
    }

    public CustomInvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CustomInvalidJwtException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
