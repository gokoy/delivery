package com.gokoy.delivery.domain.store.application;

import com.gokoy.delivery.domain.ceo.dao.CeoRepository;
import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.store.dao.StoreRepository;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.domain.store.dto.CreateStoreDto;
import com.gokoy.delivery.domain.store.dto.SimpleStoreDto;
import com.gokoy.delivery.domain.store.dto.StoreDto;
import com.gokoy.delivery.domain.store.dto.UpdateStoreDto;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.error.exception.CustomUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final CeoRepository ceoRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public SimpleResponse createStore(String userEmail, CreateStoreDto createStoreDto) {
        Ceo ceo = ceoRepository.findByEmail(userEmail).orElseThrow(NoSuchElementException::new);

        Store store = createStoreDto.toEntity();
        store.setCeo(ceo);
        ceo.addStore(store);

        storeRepository.save(store);

        return SimpleResponse.success();
    }

    public StoreDto readStore(String userEmail, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(NoSuchElementException::new);

        isAuthorized(userEmail, store);

        return StoreDto.from(store);
    }

    public List<SimpleStoreDto> readStoresByCategory(String storeType) {
        List<Store> foundStores = storeRepository.findByCategory(StoreType.valueOf(storeType));

        return foundStores
                .stream()
                .map(SimpleStoreDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public SimpleResponse updateStore(String userEmail, Long storeId, UpdateStoreDto updateStoreDto) {
        Store store = storeRepository.findById(storeId).orElseThrow(NoSuchElementException::new);

        isAuthorized(userEmail, store);

        Store storeForUpdate = updateStoreDto.toEntity();
        store.updateStore(storeForUpdate);

        return SimpleResponse.success();
    }

    @Transactional
    public SimpleResponse deleteStore(String userEmail, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(NoSuchElementException::new);

        isAuthorized(userEmail, store);

        storeRepository.deleteById(storeId);

        return SimpleResponse.success();
    }


    private boolean isAuthorized(String userEmail, Store store) {
        if (!userEmail.equals(store.getCeo().getEmail())) {
            throw new CustomUnauthorizedException();
        }

        return true;
    }
}
