package com.gokoy.delivery.domain.store.api;

import com.gokoy.delivery.domain.store.application.StoreService;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.domain.store.dto.CreateStoreRequest;
import com.gokoy.delivery.domain.store.dto.SimpleStoreResponse;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.config.validation.ValidEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Validated
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public List<SimpleStoreResponse> getSimpleStoresByCategory(@RequestParam
                                                               @ValidEnum(enumClass = StoreType.class,
                                                                       message ="Invalid value. Please enter the correct store type.")
                                                                       String storeType) {
        return storeService.getSimpleStoresByCategory(storeType.toUpperCase());
    }

    @PostMapping
    public ResponseEntity<SimpleResponse> createStore(@AuthenticationPrincipal String userEmail, @Valid @RequestBody CreateStoreRequest createStoreRequest) {
        return ResponseEntity.ok().body(storeService.createStore(userEmail, createStoreRequest));
    }
}
