package com.gokoy.delivery.domain.consumer.application;

import com.gokoy.delivery.domain.ceo.dto.CeoSignInRequest;
import com.gokoy.delivery.domain.consumer.dao.ConsumerRepository;
import com.gokoy.delivery.domain.consumer.domain.Consumer;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignInRequest;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignUpRequest;
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
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse signIn(ConsumerSignInRequest consumerSignInRequest) {
        Consumer consumer = getMember(consumerSignInRequest.getEmail());

        isPasswordCorrect(consumerSignInRequest.getPassword(), consumer.getPassword());

        return new JwtResponse(jwtTokenProvider.createToken(consumer.getEmail(), consumer.getRole()));
    }

    public Consumer getMember(String email) {
        return consumerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException(ErrorCode.EMAIL_NOT_FOUND));
    }

    private void isPasswordCorrect(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomInvalidValueException(ErrorCode.PWD_MISMATCH);
        }
    }

    @Transactional
    public SimpleResponse signUp(ConsumerSignUpRequest consumerSignUpRequest) {
        existMember(consumerSignUpRequest.getEmail());

        consumerRepository.save(consumerSignUpRequest.toEntity(passwordEncoder));

        return SimpleResponse.success();
    }

    public void existMember(String email) {
        Optional<Consumer> optionalMember = consumerRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            throw new CustomInvalidValueException(ErrorCode.EMAIL_EXISTS);
        }
    }
}
