package com.gokoy.delivery.domain.store.application;

import com.gokoy.delivery.domain.ceo.dao.CeoRepository;
import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.store.dao.StoreRepository;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.domain.store.dto.CreateStoreRequest;
import com.gokoy.delivery.domain.store.dto.SimpleStore;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final CeoRepository ceoRepository;
    private final StoreRepository storeRepository;

    public List<SimpleStore> getSimpleStoresByCategory(String storeType) {
        List<SimpleStore> simpleStores = new ArrayList<>();

        List<Store> foundStores = storeRepository.findByCategory(StoreType.valueOf(storeType));

        if (foundStores.isEmpty()) {
            throw new CustomEntityNotFoundException(ErrorCode.NO_RESULT);
        }

        for (Store store : foundStores) {
            simpleStores.add(SimpleStore.of(store));
        }

        return simpleStores;
    }


    @Transactional
    public SimpleResponse createStore(String userEmail, CreateStoreRequest createStoreRequest) {
        Ceo ceo = ceoRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.EMAIL_NOT_FOUND));

        Store store = createStoreRequest.toEntity();
        store.setCeo(ceo);
        ceo.addStore(store);

        storeRepository.save(store);

        return SimpleResponse.success();
    }
}
