package com.gokoy.delivery.domain.consumer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokoy.delivery.domain.consumer.application.ConsumerService;
import com.gokoy.delivery.domain.consumer.application.ConsumerServiceForAuth;
import com.gokoy.delivery.domain.consumer.domain.Consumer;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignInRequest;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignUpRequest;
import com.gokoy.delivery.global.common.response.JwtResponse;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsumerController.class)
class ConsumerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConsumerService consumerService;

    @MockBean
    private ConsumerServiceForAuth consumerServiceForAuth;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void sign_in_성공() throws Exception {
        //given
        String body = objectMapper.writeValueAsString(new ConsumerSignInRequest("test@naver.com", "password"));
        BDDMockito.given(consumerService.signIn(any(ConsumerSignInRequest.class)))
                .willReturn(new JwtResponse("jwt"));

        //when
        ResultActions result = mvc.perform(post("/consumer/sign-in").content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt", is("jwt")));
    }

    @Test
    public void sign_up_성공() throws Exception {
        //given
        Consumer consumer = new Consumer("test@naver.com", "password", "010-1234-5678");

        String body = objectMapper.writeValueAsString(consumer);
        BDDMockito.given(consumerService.signUp(any(ConsumerSignUpRequest.class)))
                .willReturn(SimpleResponse.success());

        //when
        ResultActions result = mvc.perform(
                post("/consumer/sign-up").content(body).contentType(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(SimpleResponse.success().getMessage())));
    }

}
