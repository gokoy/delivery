package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.Store;
import lombok.Getter;

@Getter
public class SimpleStore {
    private Long id;
    private String name;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;

    public SimpleStore(Long id, String name, Integer minimumOrderPrice, Integer deliveryTip) {
        this.id = id;
        this.name = name;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
    }

    public static SimpleStore of(Store store) {
        return new SimpleStore(
                store.getId(),
                store.getName(),
                store.getMinimumOrderPrice().getValue(),
                store.getDeliveryTip().getValue()
        );
    }
}
