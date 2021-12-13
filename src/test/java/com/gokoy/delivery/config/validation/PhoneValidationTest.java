package com.gokoy.delivery.config.validation;

import com.gokoy.delivery.global.config.validation.ValidPhone;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class PhoneValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @ParameterizedTest
    @ValueSource(strings = {"010-0000-0000", "017-000-0000", "019-1234-5678", "010-9999-9999"})
    void Phone_Custom_Validation_성공_테스트(String param) {
        //given
        PhoneValidationTestClass testClass = new PhoneValidationTestClass(param);

        //when
        Set<ConstraintViolation<PhoneValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "01-1234-5678", "010-1234-56789", "01012345678"})
    void Phone_Custom_Validation_실패_테스트(String param) {
        //given
        PhoneValidationTestClass testClass = new PhoneValidationTestClass(param);

        //when
        Set<ConstraintViolation<PhoneValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isNotEmpty();
    }

    class PhoneValidationTestClass {
        @ValidPhone
        private String phone;

        public PhoneValidationTestClass(String phone) {
            this.phone = phone;
        }
    }
}
