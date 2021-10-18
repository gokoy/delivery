package com.gokoy.delivery.global.error.exception;

public enum ErrorCode {
	EMAIL_NOT_FOUND(404, "C001", "Email not found."),
	EMAIL_EXISTS(400, "C002", "The email already exists."),
	PWD_MISMATCH(400, "C003", "The passwords do not match."),
	ARGUMENT_NOT_VALID(400, "C004", "The argument is not valid."),
	NO_RESULT(404, "C005", "No results were found for your search."),
	INVALID_JWT(403, "C006", "Invalid JWT.")
	;

	private int status;
	private final String code;
	private final String message;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}

	public int getStatus() {
		return status;
	}
}
