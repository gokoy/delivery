package com.gokoy.delivery.domain.store.dto;

import java.time.LocalTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;

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
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 번호를 입력해주세요. (ex. 010-1234-5678)")
	private String phone;

	private String introduction = "";

	@NotBlank
	private String address;

	@NotBlank
	private String detailAddress;

	@Range(min = 0, max = 1 << 7, message = "0 ~ 128 사이의 숫자를 입력해주세요. "
		+ "(2진수로 변환하여 '월화수목금토일' 순서로 확인했을 때, 1이면 영업일로 확인)")
	private Integer openDays;

	@NotBlank
	@Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$", message = "올바른 시간을 입력해주세요. (ex. 01:25)")
	private String openTime;

	@NotBlank
	@Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$", message = "올바른 시간을 입력해주세요. (ex. 01:25)")
	private String closeTime;

	@PositiveOrZero
	private Integer minimumOrderPrice;

	@NotBlank
	private String category;

	public Store toEntity() {
		return Store.builder()
			.name(this.name)
			.phone(this.phone)
			.introduction(this.introduction)
			.address(new Address(this.address, this.detailAddress))
			.operatingTime(
				new OperatingTime(this.openDays, LocalTime.parse(this.openTime), LocalTime.parse(this.closeTime)))
			.minimumOrderPrice(new Money(this.minimumOrderPrice))
			.categories(Set.of(Category.valueOf(this.category)))
			.build();
	}
}
