package com.gokoy.delivery.global.config.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gokoy.delivery.domain.member.api.MemberController;
import com.gokoy.delivery.domain.member.application.MemberService;
import com.gokoy.delivery.domain.member.application.MemberServiceForAuth;

@WebMvcTest(value = MemberController.class)
public class SecurityAuthorizedUserTest {

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
	public void forAnyone() throws Exception {
		mvc.perform(get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "normal", roles = {"NORMAL"})
	public void forNormalUser() throws Exception {
		mvc.perform(get("/normal"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void forAdminUser() throws Exception {
		mvc.perform(get("/admin"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
