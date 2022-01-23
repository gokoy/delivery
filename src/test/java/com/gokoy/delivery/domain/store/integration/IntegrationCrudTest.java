package com.gokoy.delivery.domain.store.integration;

import com.gokoy.delivery.config.IntegrationTestConfig;
import com.gokoy.delivery.domain.ceo.dao.CeoRepository;
import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.store.dao.StoreRepository;
import com.gokoy.delivery.domain.store.domain.OperatingTime;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.dto.CreateStoreDto;
import com.gokoy.delivery.domain.store.dto.UpdateStoreDto;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.Money;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import com.gokoy.delivery.global.config.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class IntegrationCrudTest extends IntegrationTestConfig {

    private static String jwt;
    private static Store initStore;

    @Autowired
    private StoreRepository storeRepository;

    @BeforeAll
    static void 초기_데이터_세팅(@Autowired CeoRepository ceoRepository,
                          @Autowired StoreRepository storeRepository,
                          @Autowired JwtTokenProvider jwtTokenProvider) {
        Ceo ceo = new Ceo("ceo@naver.com", "1234", "010-1234-5678");
        Store store = Store.builder()
                .name("Store")
                .phone("010-1234-5678")
                .introduction("introduction")
                .address(new Address("address", "detail"))
                .operatingTime(new OperatingTime(Integer.parseInt("1111111", 2), LocalTime.of(9, 0), LocalTime.of(18, 0)))
                .minimumOrderPrice(new Money(12000))
                .deliveryTip(new Money(2000))
                .build();
        ceo.addStore(store);
        store.setCeo(ceo);

        ceoRepository.save(ceo);
        initStore = storeRepository.save(store);

        jwt = jwtTokenProvider.createToken(ceo.getEmail(), ceo.getRole());
    }

    @Test
    void Store_생성() throws Exception {
        //given
        CreateStoreDto dto = createSampleStoreDto();

        //when
        //then
        mvc.perform(post("/stores")
                        .contentType("application/json; charset=utf-8")
                        .content(objectMapper.writeValueAsString(dto))
                        .header(JwtTokenProvider.AUTH_HEADER, jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()));
    }

    private CreateStoreDto createSampleStoreDto() {
        return CreateStoreDto.builder()
                .name("Store")
                .phone("010-1234-5678")
                .introduction("introduction")
                .address("address")
                .addressDetail("detail")
                .businessDay(Integer.parseInt("1111111", 2))
                .openTime("09:00:00")
                .closeTime("18:00:00")
                .minimumOrderPrice(12000)
                .deliveryTip(2000)
                .build();
    }

    @Test
    void Store_id로_조회() throws Exception {
        //given
        //when
        //then
        System.out.println(initStore.getId());
        mvc.perform(get("/stores/" + initStore.getId())
                        .header(JwtTokenProvider.AUTH_HEADER, jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(initStore.getName()))
                .andExpect(jsonPath("$.phone").value(initStore.getPhone()));
    }

    @Test
    void Store_id로_수정() throws Exception {
        //given
        UpdateStoreDto updateStoreDto = UpdateStoreDto.builder()
                .name("updatedStore")
                .phone("010-1111-1111")
                .introduction("updated introduction")
                .address("updated address")
                .addressDetail("updated detail")
                .businessDay(Integer.parseInt("1000000", 2))
                .openTime("10:00:00")
                .closeTime("19:00:00")
                .minimumOrderPrice(10000)
                .deliveryTip(1000)
                .build();

        //when
        //then
        mvc.perform(put("/stores/" + initStore.getId())
                        .header(JwtTokenProvider.AUTH_HEADER, jwt)
                        .contentType("application/json; charset=utf-8")
                        .content(objectMapper.writeValueAsString(updateStoreDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateStoreDto.getName()))
                .andExpect(jsonPath("$.phone").value(updateStoreDto.getPhone()));
    }

    @Test
    void Store_id로_삭제() throws Exception {
        //given
        //when
        //then
        mvc.perform(delete("/stores/" + initStore.getId())
                        .header(JwtTokenProvider.AUTH_HEADER, jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SimpleResponse.successMessage));
    }

}
