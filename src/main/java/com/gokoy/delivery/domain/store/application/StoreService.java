package com.gokoy.delivery.domain.store.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokoy.delivery.domain.store.dao.StoreRepository;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.dto.StoreCreateRequest;
import com.gokoy.delivery.domain.store.dto.StoreCreateResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

	private final StoreRepository storeRepository;

	@Transactional
	public StoreCreateResponse createStore(StoreCreateRequest dto) {
		Store store = storeRepository.save(dto.toEntity());

		return new StoreCreateResponse(store.getId());
	}

}
