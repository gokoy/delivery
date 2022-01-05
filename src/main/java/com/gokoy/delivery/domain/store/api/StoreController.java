package com.gokoy.delivery.domain.store.api;

import com.gokoy.delivery.domain.store.application.StoreService;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.domain.store.dto.CreateStoreDto;
import com.gokoy.delivery.domain.store.dto.SimpleStoreDto;
import com.gokoy.delivery.domain.store.dto.StoreDto;
import com.gokoy.delivery.domain.store.dto.UpdateStoreDto;
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

    @PostMapping
    public ResponseEntity<SimpleResponse> createStore(@AuthenticationPrincipal String userEmail,
                                                      @Valid @RequestBody CreateStoreDto createStoreDto) {
        return ResponseEntity.ok().body(storeService.createStore(userEmail, createStoreDto));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDto> readStore(@AuthenticationPrincipal String userEmail,
                                              @PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok().body(storeService.readStore(userEmail, storeId));
    }

    @GetMapping
    public ResponseEntity<List<SimpleStoreDto>> readStoresByCategory(@ValidEnum(
            enumClass = StoreType.class,
            message = "Invalid value. Please enter the correct store type.") String storeType) {
        return ResponseEntity.ok().body(storeService.readStoresByCategory(storeType));
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<SimpleResponse> updateStore(@AuthenticationPrincipal String userEmail,
                                                      @PathVariable("storeId") Long storeId,
                                                      @Valid @RequestBody UpdateStoreDto updateStoreDto) {
        return ResponseEntity.ok().body(storeService.updateStore(userEmail, storeId, updateStoreDto));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<SimpleResponse> deleteStore(@AuthenticationPrincipal String userEmail,
                                                      @PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok().body(storeService.deleteStore(userEmail, storeId));
    }

}
