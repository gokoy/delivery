package com.gokoy.delivery.global.error;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.gokoy.delivery.global.error.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private Timestamp timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	private String code;
	private List<String> details = new ArrayList<>();

	public ErrorResponse(ErrorCode errorCode, String path) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.status = errorCode.getStatus();
		this.error = HttpStatus.valueOf(this.status).getReasonPhrase();
		this.message = errorCode.getMessage();
		this.path = path;
		this.code = errorCode.getCode();
	}

	public ErrorResponse(ErrorCode errorCode, String path, List<String> details) {
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.status = errorCode.getStatus();
		this.error = HttpStatus.valueOf(this.status).getReasonPhrase();
		this.message = errorCode.getMessage();
		this.path = path;
		this.code = errorCode.getCode();
		this.details = details;
	}

}
