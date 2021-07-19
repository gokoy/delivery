package com.gokoy.delivery.domain.store.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OperatingTime {
	private Integer startHour;
	private Integer startMinute;
	private Integer endHour;
	private Integer endMinute;
}
