package com.gokoy.delivery.domain.user;

import com.gokoy.delivery.domain.ceo.api.CeoController;
import com.gokoy.delivery.domain.ceo.dto.CeoSignUpRequest;
import com.gokoy.delivery.domain.consumer.api.ConsumerController;
import com.gokoy.delivery.domain.consumer.dto.ConsumerSignUpRequest;
import com.gokoy.delivery.global.common.response.SimpleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TwoTypeUserTest {

    @Autowired
    private CeoController ceoController;

    @Autowired
    private ConsumerController consumerController;

    @Test
    void 두_타입_User_계정_생성_테스트() {
        //given
        ConsumerSignUpRequest consumerSignUpRequest
                = new ConsumerSignUpRequest("consumer@naver.com", "password", "010-1234-5678");
        CeoSignUpRequest ceoSignUpRequest
                = new CeoSignUpRequest("ceo@naver.com", "password", "010-1234-5678");

        //when
        ResponseEntity<SimpleResponse> consumerSignUpResponse =
                consumerController.signUp(consumerSignUpRequest);
        ResponseEntity<SimpleResponse> ceoSignUpResponse =
                ceoController.signUp(ceoSignUpRequest);
    }
}

