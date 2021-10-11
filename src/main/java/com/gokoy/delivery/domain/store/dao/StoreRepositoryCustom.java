package com.gokoy.delivery.domain.store.dao;

import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.domain.store.domain.Store;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepositoryCustom {
    List<Store> findByCategory(StoreType storeType);
}
