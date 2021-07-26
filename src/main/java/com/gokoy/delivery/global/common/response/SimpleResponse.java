package com.gokoy.delivery.global.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleResponse {

	private String message;

	public static SimpleResponse success() {
		return new SimpleResponse("success");
	}

}
