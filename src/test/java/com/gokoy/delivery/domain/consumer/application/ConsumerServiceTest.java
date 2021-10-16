package com.gokoy.delivery.domain.consumer.application;

import com.gokoy.delivery.domain.consumer.dao.ConsumerRepository;
import com.gokoy.delivery.domain.consumer.domain.Consumer;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignInRequest;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignUpRequest;
import com.gokoy.delivery.global.common.model.User;
import com.gokoy.delivery.global.common.response.JwtResponse;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.CustomInvalidValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    @InjectMocks
    private ConsumerService consumerService;

    @Mock
    private ConsumerRepository consumerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void sign_in_이메일_존재_안함() {
        //given
        ConsumerSignInRequest userInput = new ConsumerSignInRequest("test@naver.com", "password");
        given(consumerRepository.findByEmail(userInput.getEmail())).willReturn(Optional.empty());

        //when
        //then
        assertThrows(CustomEntityNotFoundException.class, () -> consumerService.signIn(userInput));
    }

    @Test
    public void sign_in_패스워드_불일치() {
        //given
        ConsumerSignInRequest userInput = new ConsumerSignInRequest("test@naver.com", "password");
        Consumer foundConsumer = new Consumer("test@naver.com", "pass", "nickname");
        given(consumerRepository.findByEmail(userInput.getEmail())).willReturn(Optional.of(foundConsumer));

        //when
        //then
        assertThrows(CustomInvalidValueException.class, () -> consumerService.signIn(userInput));
    }

    @Test
    public void sign_in_성공() {
        //given
        ConsumerSignInRequest userInput = new ConsumerSignInRequest("test@naver.com", "password");
        Consumer foundConsumer = new Consumer("test@naver.com", "password", "010-1234-5678");

        given(consumerRepository.findByEmail(userInput.getEmail())).willReturn(Optional.of(foundConsumer));
        given(passwordEncoder.matches(userInput.getPassword(), foundConsumer.getPassword()))
                .willReturn(true);
        given(jwtTokenProvider.createToken(foundConsumer.getEmail(), foundConsumer.getRole()))
                .willReturn("jwt");

        //when
        JwtResponse result = consumerService.signIn(userInput);

        //then
        assertThat(result.getJwt()).isEqualTo("jwt");
    }

    @Test
    public void sign_up_이메일_이미_존재() {
        //given
        ConsumerSignUpRequest userInput = new ConsumerSignUpRequest("test@naver.com", "password", "010-1234-5678");
        Consumer foundConsumer = new Consumer("test@naver.com", "password", "010-1234-5678");
        given(consumerRepository.findByEmail(userInput.getEmail()))
                .willReturn(java.util.Optional.of(foundConsumer));

        //when
        //then
        assertThrows(CustomInvalidValueException.class, () -> consumerService.signUp(userInput));
    }

    @Test
    public void sign_up_성공() {
        //given
        ConsumerSignUpRequest userInput = new ConsumerSignUpRequest("test@naver.com", "password", "nickname");
        given(consumerRepository.findByEmail(userInput.getEmail())).willReturn(Optional.empty());
        given(passwordEncoder.encode(userInput.getPassword())).willReturn("password");
        Consumer returnConsumer = new Consumer("test@naver.com", "password", "nickname");
        given(consumerRepository.save(any(Consumer.class))).willReturn(returnConsumer);

        //when
        SimpleResponse response = consumerService.signUp(userInput);

        //then
        assertThat(response.getMessage()).isEqualTo(SimpleResponse.success().getMessage());
    }

}
