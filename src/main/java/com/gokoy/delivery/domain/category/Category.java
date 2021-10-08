package com.gokoy.delivery.domain.category;


import com.gokoy.delivery.domain.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    public Category(StoreType storeType) {
        this.storeType = storeType;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
