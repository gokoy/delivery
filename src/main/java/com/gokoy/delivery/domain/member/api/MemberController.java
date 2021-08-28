package com.gokoy.delivery.domain.member.api;

import com.gokoy.delivery.domain.member.application.MemberService;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody MemberSignInRequest memberSignInRequest) {
        return ResponseEntity.ok().body(memberService.signIn(memberSignInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        return ResponseEntity.ok().body(memberService.signUp(memberSignUpRequest));
    }
}
