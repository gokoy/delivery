package com.gokoy.delivery.domain.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gokoy.delivery.domain.member.dao.MemberRepository;
import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.ErrorCode;
import com.gokoy.delivery.global.error.exception.CustomInvalidValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public String signIn(MemberSignInRequest memberSignInRequest) {
		Member member = getMember(memberSignInRequest.getEmail());

		passwordMatching(memberSignInRequest.getPassword(), member.getPassword());

		return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
	}

	public Member saveMember(MemberSignUpRequest memberSignUpRequest) {
		if (existMember(memberSignUpRequest.getEmail())) {
			throw new CustomInvalidValueException(ErrorCode.EMAIL_EXISTS);
		}

		return memberRepository.save(memberSignUpRequest.toEntity(passwordEncoder));
	}

	public Member getMember(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.EMAIL_NOT_FOUND));
	}

	public boolean existMember(String email) {
		Member member = memberRepository.findByEmail(email).orElse(null);

		return member != null;
	}

	private boolean passwordMatching(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new CustomInvalidValueException(ErrorCode.PWD_MISMATCH);
		}

		return true;
	}

}
