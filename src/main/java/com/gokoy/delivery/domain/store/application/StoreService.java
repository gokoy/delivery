package com.gokoy.delivery.domain.store.application;

import com.gokoy.delivery.domain.ceo.dao.CeoRepository;
import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.store.dao.StoreRepository;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.domain.store.dto.SimpleStoreResponse;
import com.gokoy.delivery.domain.store.dto.StoreRequest;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.CustomInvalidValueException;
import com.gokoy.delivery.global.error.exception.CustomUnauthorizedException;
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

    public List<SimpleStoreResponse> getSimpleStoresByCategory(String storeType) {
        List<SimpleStoreResponse> simpleStoreResponses = new ArrayList<>();

        List<Store> foundStores = storeRepository.findByCategory(StoreType.valueOf(storeType));

        if (foundStores.isEmpty()) {
            throw new CustomEntityNotFoundException(ErrorCode.NO_RESULT);
        }

        for (Store store : foundStores) {
            simpleStoreResponses.add(SimpleStoreResponse.of(store));
        }

        return simpleStoreResponses;
    }


    @Transactional
    public SimpleResponse createStore(String userEmail, StoreRequest storeRequest) {
        Ceo ceo = ceoRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.EMAIL_NOT_FOUND));

        Store store = storeRequest.toEntity();
        store.setCeo(ceo);
        ceo.addStore(store);

        storeRepository.save(store);

        return SimpleResponse.success();
    }

    @Transactional
    public SimpleResponse updateStore(String userEmail, Long storeId, StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.NO_RESULT));

        if (!userEmail.equals(store.getCeo().getEmail())) {
            throw new CustomUnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        Store storeForUpdate = storeRequest.toEntity();
        store.updateStore(storeForUpdate);

        return SimpleResponse.success();
    }
}
