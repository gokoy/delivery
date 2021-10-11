package com.gokoy.delivery.domain.store.dao;

import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gokoy.delivery.domain.store.domain.QCategory.category;
import static com.gokoy.delivery.domain.store.domain.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> findByCategory(StoreType storeType) {
        return queryFactory
                .selectFrom(store)
                .join(store.categories, category)
                .where(category.storeType.eq(storeType))
                .fetch();
    }
}
