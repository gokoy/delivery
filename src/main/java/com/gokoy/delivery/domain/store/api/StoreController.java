package com.gokoy.delivery.domain.store.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokoy.delivery.domain.store.application.StoreService;
import com.gokoy.delivery.domain.store.dto.StoreCreateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<?> createStore(@RequestBody @Valid StoreCreateRequest storeCreateRequest) {
		return ResponseEntity.ok().body(storeService.createStore(storeCreateRequest));
	}

}
