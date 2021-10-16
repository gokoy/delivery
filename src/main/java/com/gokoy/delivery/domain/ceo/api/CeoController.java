package com.gokoy.delivery.domain.ceo.api;

import com.gokoy.delivery.domain.ceo.application.CeoService;
import com.gokoy.delivery.domain.ceo.dto.CeoSignInRequest;
import com.gokoy.delivery.domain.ceo.dto.CeoSignUpRequest;
import com.gokoy.delivery.global.common.response.JwtResponse;
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
@RequestMapping("/ceo")
public class CeoController {

    private final CeoService ceoService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody CeoSignInRequest ceoSignInRequest) {
        return ResponseEntity.ok().body(ceoService.signIn(ceoSignInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SimpleResponse> signUp(@Valid @RequestBody CeoSignUpRequest ceoSignUpRequest) {
        return ResponseEntity.ok().body(ceoService.signUp(ceoSignUpRequest));
    }
}
