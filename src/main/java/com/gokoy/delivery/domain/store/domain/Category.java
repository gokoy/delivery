package com.gokoy.delivery.domain.store.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    private StoreType storeType;

    public Category(StoreType storeType) {
        this.storeType = storeType;
    }
}
