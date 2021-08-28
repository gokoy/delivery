package com.gokoy.delivery.domain.store.dto;

import java.time.LocalTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.OperatingTime;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.Money;
import com.gokoy.delivery.global.config.validation.Enum;

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

	@Builder.Default
	private String introduction = "";

	@NotBlank
	private String addressName;

	@NotBlank
	private String addressDetail;

	@Range(min = 0, max = 1 << 7, message = "0 ~ 128 사이의 숫자를 입력해주세요. "
		+ "(2진수로 변환하여 '월화수목금토일' 순서로 확인했을 때, 1이면 영업일로 확인)")
	private Integer businessDay;

	@NotBlank
	@Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$", message = "24시 기준의 올바른 시간을 입력해주세요. (ex. 01:25)")
	private String openTime;

	@NotBlank
	@Pattern(regexp = "^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$", message = "24시 기준의 올바른 시간을 입력해주세요. (ex. 01:25)")
	private String closeTime;

	@Positive
	private Integer minimumOrderPrice;

	@Positive
	private Integer deliveryTip;

	@Enum(enumClass = Category.class, message = "올바른 카테고리 명을 입력해주세요.")
	private String category;

	public Store toEntity() {
		return Store.builder()
			.name(this.name)
			.phone(this.phone)
			.introduction(this.introduction)
			.address(new Address(this.addressName, this.addressDetail))
			.operatingTime(
				new OperatingTime(this.businessDay, LocalTime.parse(this.openTime), LocalTime.parse(this.closeTime)))
			.minimumOrderPrice(new Money(this.minimumOrderPrice))
			.deliveryTip(new Money(this.deliveryTip))
			.categories(Set.of(Category.valueOf(this.category)))
			.build();
	}
}
