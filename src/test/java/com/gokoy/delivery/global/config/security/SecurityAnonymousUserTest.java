package com.gokoy.delivery.global.config.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gokoy.delivery.domain.member.api.MemberController;
import com.gokoy.delivery.domain.member.application.MemberService;
import com.gokoy.delivery.domain.member.application.MemberServiceForAuth;

@WebMvcTest(value = MemberController.class)
@WithAnonymousUser
public class SecurityAnonymousUserTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private MemberServiceForAuth memberServiceForAuth;

	@MockBean
	private MemberService memberService;

	@Test
	public void forAnyone() throws Exception {
		mvc.perform(get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void forAuthorizedUser() throws Exception {
		mvc.perform(get("/normal"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
