package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.global.config.validation.ValidLocalTime;
import com.gokoy.delivery.global.config.validation.ValidPhone;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Builder
public class StoreDto {

    private Long id;

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

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .phone(store.getPhone())
                .introduction(store.getIntroduction())
                .address(store.getAddress().getAddress())
                .addressDetail(store.getAddress().getDetail())
                .businessDay(store.getOperatingTime().getBusinessDay())
                .openTime(store.getOperatingTime().getOpenTime().toString())
                .closeTime(store.getOperatingTime().getCloseTime().toString())
                .minimumOrderPrice(store.getMinimumOrderPrice().getValue())
                .deliveryTip(store.getDeliveryTip().getValue())
                .build();
    }
}
