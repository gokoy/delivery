package com.gokoy.delivery.domain.model;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {
	private String unit;
	private Integer value;
}
