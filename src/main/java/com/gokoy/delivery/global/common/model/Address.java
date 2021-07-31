package com.gokoy.delivery.global.common.model;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String address;

	private String detail;

	public Address(String address, String detail) {
		this.address = address;
		this.detail = detail;
	}
}
