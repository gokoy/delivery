package com.gokoy.delivery.domain.order.domain;

import javax.persistence.Embeddable;

import com.gokoy.delivery.domain.model.Money;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderFood {

	private String name;

	private Money price;
}
