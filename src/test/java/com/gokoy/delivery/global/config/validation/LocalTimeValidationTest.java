package com.gokoy.delivery.global.config.validation;

import com.gokoy.delivery.global.config.validation.ValidLocalTime;
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

public class LocalTimeValidationTest {

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
    @ValueSource(strings = {"00:00:00", "23:59:59", "01:12:55", "22:56:00"})
    void LocalTime_Custom_Valid_성공_테스트(String param) {
        //given
        LocalTimeValidationTestClass testClass = new LocalTimeValidationTestClass(param);

        //when
        Set<ConstraintViolation<LocalTimeValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "aaa", "01:14", "24:00:00", "25:00:00", "10:80:00", "20:50:99"})
    void LocalTime_Custom_Valid_실패_테스트(String param) {
        //given
        LocalTimeValidationTestClass testClass = new LocalTimeValidationTestClass(param);

        //when
        Set<ConstraintViolation<LocalTimeValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isNotEmpty();
    }

    public static class LocalTimeValidationTestClass {
        @ValidLocalTime
        private final String localTime;

        public LocalTimeValidationTestClass(String localTime) {
            this.localTime = localTime;
        }
    }
}
