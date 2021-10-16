package com.gokoy.delivery.global.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.gokoy.delivery.domain.consumer.api.ConsumerController;
import com.gokoy.delivery.domain.consumer.application.ConsumerService;
import com.gokoy.delivery.domain.consumer.application.ConsumerServiceForAuth;

@WebMvcTest(value = ConsumerController.class)
public class SecurityTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@MockBean
	private ConsumerServiceForAuth consumerServiceForAuth;

	@MockBean
	private ConsumerService consumerService;

//	@Test
//	@WithMockUser(username = "member", roles = {"MEMBER"})
//	public void 회원만_접근_가능한_api_성공() throws Exception {
//		mvc.perform(get("/member"))
//			.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	@WithAnonymousUser
//	public void 회원만_접근_가능한_api_실패() throws Exception {
//		mvc.perform(get("/member"))
//			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
//	}
}
