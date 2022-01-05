package com.gokoy.delivery.domain.store.api;

import com.gokoy.delivery.domain.store.application.StoreService;
import com.gokoy.delivery.global.config.security.JwtAuthenticationFilter;
import com.gokoy.delivery.global.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = StoreController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class}))
class StoreControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private StoreService storeService;
//
//    @Test
//    @WithMockUser(roles = "CEO")
//    void getSimpleStoresByCategory_유효성_검사() throws Exception {
//        //given
//        BDDMockito
//                .given(storeService.readStoresByCategory(any(String.class)))
//                .willReturn(null);
//
//        LinkedMultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
//        requestParam.set("storeType", null);
//
//        //when
//        ResultActions result = mvc.perform(get("/stores")
//                        .params(requestParam)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        //then
//        result
//                .andExpect(status().is4xxClientError());
//
//    }

}