package com.gokoy.delivery.global.config.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gokoy.delivery.domain.member.api.MemberController;
import com.gokoy.delivery.domain.member.application.MemberService;
import com.gokoy.delivery.domain.member.application.MemberServiceForAuth;

@WebMvcTest(value = MemberController.class)
public class SecurityTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private MemberServiceForAuth memberServiceForAuth;

	@MockBean
	private MemberService memberService;

	@Test
	@WithMockUser
	public void 모든_사용자_접근_가능한_api() throws Exception {
		mvc.perform(get("/anyone"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "member", roles = {"MEMBER"})
	public void 회원만_접근_가능한_api_성공() throws Exception {
		mvc.perform(get("/member"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithAnonymousUser
	public void 회원만_접근_가능한_api_실패() throws Exception {
		mvc.perform(get("/member"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
