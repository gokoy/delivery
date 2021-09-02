package com.gokoy.delivery.global.common.model;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

	private String name;

	private String detail;

	public Address(String name, String detail) {
		this.name = name;
		this.detail = detail;
	}
}
