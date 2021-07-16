package com.gokoy.delivery.domain.member.api;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokoy.delivery.domain.member.application.MemberService;
import com.gokoy.delivery.domain.member.application.MemberServiceForAuth;
import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.domain.member.dto.MemberSignInRequest;
import com.gokoy.delivery.domain.member.dto.MemberSignUpRequest;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private MemberServiceForAuth memberServiceForAuth;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void signIn() throws Exception {
		//given
		String body = objectMapper.writeValueAsString(new MemberSignInRequest("gokoy@naver.com", "password"));
		BDDMockito.given(memberService.signIn(any(MemberSignInRequest.class))).willReturn("token");

		//when
		ResultActions result = mvc.perform(post("/sign-in").content(body).contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		//then
		result
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void signUp() throws Exception {
		//given
		Member member = new Member("gokoy@naver.com", "password", Collections.singletonList("NORMAL"));
		String body = objectMapper.writeValueAsString(member);
		BDDMockito.given(memberService.signUp(any(MemberSignUpRequest.class))).willReturn(member);

		//when
		ResultActions result = mvc.perform(
			post("/sign-up").content(body).contentType(MediaType.APPLICATION_JSON)).andDo(print());

		//then
		result
			.andExpect(status().isOk())
			.andDo(print());
	}

}
