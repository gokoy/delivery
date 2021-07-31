package com.gokoy.delivery.domain.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gokoy.delivery.domain.member.dao.MemberRepository;
import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignInResponse;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;
import com.gokoy.delivery.global.common.response.SimpleResponse;
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
	public void sign_in_이메일_존재_안함() {
		//given
		MemberSignInRequest userInput = new MemberSignInRequest("test@naver.com", "password");
		given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.empty());

		//when
		//then
		assertThrows(CustomEntityNotFoundException.class, () -> memberService.signIn(userInput));
	}

	@Test
	public void sign_in_패스워드_불일치() {
		//given
		MemberSignInRequest userInput = new MemberSignInRequest("test@naver.com", "password");
		Member foundMember = new Member("test@naver.com", "pass", "nickname");
		given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.of(foundMember));

		//when
		//then
		assertThrows(CustomInvalidValueException.class, () -> memberService.signIn(userInput));
	}

	@Test
	public void sign_in_성공() {
		//given
		MemberSignInRequest userInput = new MemberSignInRequest("test@naver.com", "password");
		Member foundMember = new Member("test@naver.com", "password", "nickname");

		given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.of(foundMember));
		given(passwordEncoder.matches(userInput.getPassword(), foundMember.getPassword()))
			.willReturn(true);
		given(jwtTokenProvider.createToken(foundMember.getEmail(), foundMember.getRole()))
			.willReturn("jwt");

		//when
		MemberSignInResponse result = memberService.signIn(userInput);

		//then
		assertThat(result.getJwt()).isEqualTo("jwt");
	}

	@Test
	public void sign_up_이메일_이미_존재() {
		//given
		MemberSignUpRequest userInput = new MemberSignUpRequest("test@naver.com", "password", "nickname");
		Member foundMember = new Member("test@naver.com", "password", "nickname");
		given(memberRepository.findByEmail(userInput.getEmail()))
			.willReturn(java.util.Optional.of(foundMember));

		//when
		//then
		assertThrows(CustomInvalidValueException.class, () -> memberService.signUp(userInput));
	}

	@Test
	public void sign_up_성공() {
		//given
		MemberSignUpRequest userInput = new MemberSignUpRequest("test@naver.com", "password", "nickname");
		given(memberRepository.findByEmail(userInput.getEmail())).willReturn(Optional.empty());
		given(passwordEncoder.encode(userInput.getPassword())).willReturn("password");
		Member returnMember = new Member("test@naver.com", "password", "nickname");
		given(memberRepository.save(any(Member.class))).willReturn(returnMember);

		//when
		SimpleResponse response = memberService.signUp(userInput);

		//then
		assertThat(response.getMessage()).isEqualTo(SimpleResponse.success().getMessage());
	}

}
