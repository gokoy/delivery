package com.gokoy.delivery.domain.member.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokoy.delivery.domain.member.application.MemberService;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/")
	public String startPage() {
		return "startPage";
	}

	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody MemberSignInRequest memberSignInRequest) {
		return ResponseEntity.ok().body(memberService.signIn(memberSignInRequest));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
		return ResponseEntity.ok().body(memberService.saveMember(memberSignUpRequest));
	}
}
