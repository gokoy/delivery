package com.gokoy.delivery.domain.store.dto;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class StoreSimpleResponse {
    private Long id;
    private String name;
    private Integer businessDay;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;
    private Set<String> categories;

    public static StoreSimpleResponse getInstance(Store store) {
        return StoreSimpleResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .businessDay(store.getOperatingTime().getBusinessDay())
                .openTime(store.getOperatingTime().getOpenTime())
                .closeTime(store.getOperatingTime().getCloseTime())
                .minimumOrderPrice(store.getMinimumOrderPrice().getValue())
                .deliveryTip(store.getDeliveryTip().getValue())
                .categories(store.getCategories().stream().map(Category::getType).collect(Collectors.toSet()))
                .build();
    }
}
