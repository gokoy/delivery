package com.gokoy.delivery.domain.store.dao;

import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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
        return getStoresJPAQuery(storeType)
                .fetch();
    }

    @Override
    public List<Store> findByCategoryOrderByDeliveryTipAsc(StoreType storeType) {
        return getStoresJPAQuery(storeType)
                .orderBy(store.deliveryTip.value.asc())
                .fetch();
    }

    @Override
    public List<Store> findByCategoryBetweenMinimumOrderPrice(StoreType storeType, Integer minPrice, Integer maxPrice) {
        return getStoresJPAQuery(storeType)
                .where(store.minimumOrderPrice.value.between(minPrice, maxPrice))
                .orderBy(store.minimumOrderPrice.value.asc())
                .fetch();
    }

    private JPAQuery<Store> getStoresJPAQuery(StoreType storeType) {
        return queryFactory
                .selectFrom(store)
                .join(store.categories, category)
                .where(eqCategory(storeType));
    }

    private BooleanExpression eqCategory(StoreType storeType) {
        if (storeType == null) {
            return null;
        }

        return category.storeType.eq(storeType);
    }
}
