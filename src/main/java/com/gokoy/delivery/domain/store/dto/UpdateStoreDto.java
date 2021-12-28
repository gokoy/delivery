package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.OperatingTime;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.Money;
import com.gokoy.delivery.global.config.validation.ValidLocalTime;
import com.gokoy.delivery.global.config.validation.ValidPhone;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class UpdateStoreDto {

    private String name;

    @ValidPhone
    private String phone;

    private String introduction;

    private String address;

    private String addressDetail;

    @PositiveOrZero
    private Integer businessDay;

    @ValidLocalTime
    private String openTime;

    @ValidLocalTime
    private String closeTime;

    @PositiveOrZero
    private Integer minimumOrderPrice;

    @PositiveOrZero
    private Integer deliveryTip;

    public Store toEntity() {
        return Store.builder()
                .name(this.name)
                .introduction(introduction)
                .address(new Address(address, addressDetail))
                .operatingTime(new OperatingTime(businessDay, LocalTime.parse(openTime), LocalTime.parse(closeTime)))
                .phone(phone)
                .minimumOrderPrice(new Money(this.minimumOrderPrice))
                .build();
    }
}
