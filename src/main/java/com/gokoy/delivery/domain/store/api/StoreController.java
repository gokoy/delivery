package com.gokoy.delivery.domain.store.api;

import com.gokoy.delivery.domain.store.application.StoreService;
import com.gokoy.delivery.domain.store.dto.StoreRequest;
import com.gokoy.delivery.domain.store.dto.SimpleStore;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public List<SimpleStore> getSimpleStoresByCategory(String storeType) {
        return storeService.getSimpleStoresByCategory(storeType);
    }

    @PostMapping
    public ResponseEntity<SimpleResponse> createStore(StoreRequest storeRequest) {
        return ResponseEntity.ok().body(storeService.createStore(storeRequest));
    }
}
