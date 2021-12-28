package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.Store;
import lombok.Getter;

@Getter
public class SimpleStoreDto {
    private Long id;
    private String name;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;

    public SimpleStoreDto(Long id, String name, Integer minimumOrderPrice, Integer deliveryTip) {
        this.id = id;
        this.name = name;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
    }

    public static SimpleStoreDto from(Store store) {
        return new SimpleStoreDto(
                store.getId(),
                store.getName(),
                store.getMinimumOrderPrice().getValue(),
                store.getDeliveryTip().getValue()
        );
    }
}
