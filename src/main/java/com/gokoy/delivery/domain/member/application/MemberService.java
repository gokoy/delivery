package com.gokoy.delivery.domain.member.application;

import com.gokoy.delivery.domain.member.dao.MemberRepository;
import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignInResponse;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.CustomInvalidValueException;
import com.gokoy.delivery.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberSignInResponse signIn(MemberSignInRequest memberSignInRequest) {
        Member member = getMember(memberSignInRequest.getEmail());

        isPasswordCorrect(memberSignInRequest.getPassword(), member.getPassword());

        return new MemberSignInResponse(jwtTokenProvider.createToken(member.getEmail(), member.getRole()));
    }

    public Member getMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.EMAIL_NOT_FOUND));
    }

    private void isPasswordCorrect(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomInvalidValueException(ErrorCode.PWD_MISMATCH);
        }
    }

    @Transactional
    public SimpleResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        existMember(memberSignUpRequest.getEmail());

        memberRepository.save(memberSignUpRequest.toEntity(passwordEncoder));

        return SimpleResponse.success();
    }

    public void existMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            throw new CustomInvalidValueException(ErrorCode.EMAIL_EXISTS);
        }
    }
}
