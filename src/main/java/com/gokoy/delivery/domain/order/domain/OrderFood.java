package com.gokoy.delivery.domain.order.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.gokoy.delivery.global.common.model.Money;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderFood {

	private String name;

	@Embedded
	private Money price;
}
