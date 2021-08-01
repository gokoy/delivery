package com.gokoy.delivery.domain.store.dto;

import java.time.LocalTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.OperatingTime;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.Money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateRequest {

	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
	private String phone;

	private String introduction = "";

	@NotBlank
	private String address;

	@NotBlank
	private String detailAddress;

	@NotBlank
	private Integer openDays;

	@NotBlank
	private LocalTime openTime;

	@NotBlank
	private LocalTime closeTime;

	@NotBlank
	private Integer minimumOrderPrice;

	@NotBlank
	private String category;

	public Store toEntity() {
		return Store.builder()
			.name(this.name)
			.phone(this.phone)
			.introduction(this.introduction)
			.address(new Address(this.address, this.detailAddress))
			.operatingTime(new OperatingTime(this.openDays, this.openTime, this.closeTime))
			.minimumOrderPrice(new Money(this.minimumOrderPrice))
			.categories(Set.of(Category.valueOf(this.category)))
			.build();
	}
}
