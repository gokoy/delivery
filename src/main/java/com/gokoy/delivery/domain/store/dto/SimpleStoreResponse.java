package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.Store;
import lombok.Getter;

@Getter
public class SimpleStoreResponse {
    private Long id;
    private String name;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;

    public SimpleStoreResponse(Long id, String name, Integer minimumOrderPrice, Integer deliveryTip) {
        this.id = id;
        this.name = name;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
    }

    public static SimpleStoreResponse of(Store store) {
        return new SimpleStoreResponse(
                store.getId(),
                store.getName(),
                store.getMinimumOrderPrice().getValue(),
                store.getDeliveryTip().getValue()
        );
    }
}
