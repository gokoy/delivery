package com.gokoy.delivery.domain.store.domain;

import java.time.LocalTime;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OperatingTime {

	private Integer day;

	private LocalTime startTime;

	private LocalTime endTime;

	public OperatingTime(Integer day, LocalTime startTime, LocalTime endTime) {
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
