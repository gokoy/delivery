package com.gokoy.delivery.domain.ceo.application;

import com.gokoy.delivery.domain.ceo.dao.CeoRepository;
import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.ceo.dto.CeoSignInRequest;
import com.gokoy.delivery.domain.ceo.dto.CeoSignUpRequest;
import com.gokoy.delivery.global.common.response.JwtResponse;
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
public class CeoService {

    private final CeoRepository ceoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse signIn(CeoSignInRequest ceoSignInRequest) {
        Ceo ceo = getMember(ceoSignInRequest.getEmail());

        isPasswordCorrect(ceoSignInRequest.getPassword(), ceo.getPassword());

        return new JwtResponse(jwtTokenProvider.createToken(ceo.getEmail(), ceo.getRole()));
    }

    public Ceo getMember(String email) {
        return ceoRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.EMAIL_NOT_FOUND));
    }

    private void isPasswordCorrect(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomInvalidValueException(ErrorCode.PWD_MISMATCH);
        }
    }

    @Transactional
    public SimpleResponse signUp(CeoSignUpRequest ceoSignUpRequest) {
        existMember(ceoSignUpRequest.getEmail());

        ceoRepository.save(ceoSignUpRequest.toEntity(passwordEncoder));

        return SimpleResponse.success();
    }

    public void existMember(String email) {
        Optional<Ceo> optionalMember = ceoRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            throw new CustomInvalidValueException(ErrorCode.EMAIL_EXISTS);
        }
    }
}
