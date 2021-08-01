package com.gokoy.delivery.domain.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gokoy.delivery.domain.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}
