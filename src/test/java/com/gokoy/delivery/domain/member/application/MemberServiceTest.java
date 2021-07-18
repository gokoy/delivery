package com.gokoy.delivery.domain.member.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gokoy.delivery.domain.member.dao.MemberRepository;
import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.CustomInvalidValueException;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@Test
	public void signIn_이메일_존재_안함() {
		//given
		MemberSignInRequest userInput = new MemberSignInRequest("gokoy@naver.com", "password");
		BDDMockito.given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.empty());

		//when
		//then
		assertThrows(CustomEntityNotFoundException.class, () -> memberService.signIn(userInput));
	}

	@Test
	public void signIn_패스워드_불일치() {
		//given
		MemberSignInRequest userInput = new MemberSignInRequest("gokoy@naver.com", "password");
		Member foundMember = new Member("gokoy@naver.com", "pass", Collections.singletonList("NORMAL"));
		BDDMockito.given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.of(foundMember));

		//when
		//then
		assertThrows(CustomInvalidValueException.class, () -> memberService.signIn(userInput));
	}

	@Test
	public void signIn_정상_로그인() {
		//given
		MemberSignInRequest userInput = new MemberSignInRequest("gokoy@naver.com", "password");
		Member foundMember = new Member("gokoy@naver.com", "password", Collections.singletonList("NORMAL"));

		BDDMockito.given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.of(foundMember));
		BDDMockito.given(passwordEncoder.matches(userInput.getPassword(), foundMember.getPassword()))
			.willReturn(true);
		BDDMockito.given(jwtTokenProvider.createToken(foundMember.getEmail(), foundMember.getRoles()))
			.willReturn("token");

		//when
		String result = memberService.signIn(userInput);

		//then
		Assertions.assertThat(result).isEqualTo("token");
	}

	@Test
	public void signUp_이메일_이미_존재() {
		//given
		MemberSignUpRequest userInput = new MemberSignUpRequest("gokoy@naver.com", "password",
			Collections.singletonList("NORMAL"));
		Member foundMember = new Member("gokoy@naver.com", "password", Collections.singletonList("NORMAL"));
		BDDMockito.given(memberRepository.findByEmail(userInput.getEmail()))
			.willReturn(java.util.Optional.of(foundMember));

		//when
		//then
		assertThrows(CustomInvalidValueException.class, () -> memberService.signUp(userInput));
	}

	@Test
	public void signUp_정상처리() {
		//given
		MemberSignUpRequest userInput = new MemberSignUpRequest("gokoy@naver.com", "password",
			Collections.singletonList("NORMAL"));
		BDDMockito.given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.empty());
		BDDMockito.given(passwordEncoder.encode(userInput.getPassword())).willReturn("password");
		Member returnMember = new Member("gokoy@naver.com", "password",
			Collections.singletonList("NORMAL"));
		BDDMockito.given(memberRepository.save(any(Member.class))).willReturn(returnMember);

		//when
		Member result = memberService.signUp(userInput);

		//then
		Assertions.assertThat(result.getEmail()).isEqualTo(userInput.getEmail());
	}

}
