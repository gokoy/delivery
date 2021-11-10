package com.gokoy.delivery.domain.store.api;

import com.gokoy.delivery.domain.store.application.StoreService;
import com.gokoy.delivery.domain.store.dto.CreateStoreRequest;
import com.gokoy.delivery.domain.store.dto.SimpleStore;
import com.gokoy.delivery.domain.store.dto.StoreRequestForType;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public List<SimpleStore> getSimpleStoresByCategory(@Valid StoreRequestForType storeRequestForType) {
        return storeService.getSimpleStoresByCategory(storeRequestForType.getStoreType().toUpperCase());
    }

    @PostMapping
    public ResponseEntity<SimpleResponse> createStore(@AuthenticationPrincipal User user, CreateStoreRequest createStoreRequest) {
        return ResponseEntity.ok().body(storeService.createStore(user.getUsername(), createStoreRequest));
    }
}
