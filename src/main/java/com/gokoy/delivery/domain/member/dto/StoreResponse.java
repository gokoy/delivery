package com.gokoy.delivery.domain.member.dto;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String phone;
    private String introduction;
    private String addressName;
    private String addressDetail;
    private Integer businessDay;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minimumOrderPrice;
    private Integer deliveryTip;
    private Set<String> categories;

    public static StoreResponse getInstance(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .phone(store.getPhone())
                .introduction(store.getIntroduction())
                .addressName(store.getAddress().getName())
                .addressDetail(store.getAddress().getDetail())
                .businessDay(store.getOperatingTime().getBusinessDay())
                .openTime(store.getOperatingTime().getOpenTime())
                .closeTime(store.getOperatingTime().getCloseTime())
                .minimumOrderPrice(store.getMinimumOrderPrice().getValue())
                .deliveryTip(store.getDeliveryTip().getValue())
                .categories(store.getCategories().stream().map(Category::getType).collect(Collectors.toSet()))
                .build();
    }
}
