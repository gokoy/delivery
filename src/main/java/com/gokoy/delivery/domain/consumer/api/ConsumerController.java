package com.gokoy.delivery.domain.consumer.api;

import com.gokoy.delivery.domain.consumer.application.ConsumerService;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignInRequest;
import com.gokoy.delivery.global.common.response.JwtResponse;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignUpRequest;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody ConsumerSignInRequest consumerSignInRequest) {
        return ResponseEntity.ok().body(consumerService.signIn(consumerSignInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SimpleResponse> signUp(@Valid @RequestBody ConsumerSignUpRequest consumerSignUpRequest) {
        return ResponseEntity.ok().body(consumerService.signUp(consumerSignUpRequest));
    }
}
