package com.gokoy.delivery.domain.store.domain;

import java.time.LocalTime;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OperatingTime {

	private Integer businessDay;

	private LocalTime openTime;

	private LocalTime closeTime;

	public OperatingTime(Integer businessDay, LocalTime openTime, LocalTime closeTime) {
		this.businessDay = businessDay;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}
}
