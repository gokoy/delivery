package com.gokoy.delivery.config.validation;

import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.global.config.validation.ValidEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class EnumValidationTest {

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
    @ValueSource(strings = {"korean", "school_food", "dessert", "japanese",
            "chicken", "pizza", "asian_western", "chinese", "jokbal_bossam", "midnight_meal",
            "steam_soup", "lunch_box", "fast_food", "vegetarian"})
    void Enum_Custom_Validator_올바른_인자_테스트(String param) {
        //given
        EnumValidationTestClass testClass = new EnumValidationTestClass(param);

        //when
        Set<ConstraintViolation<EnumValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "korea", "school_foodd"})
    void Enum_Custom_Validator_예외_발생_인자_테스트(String param) {
        //given
        EnumValidationTestClass testClass = new EnumValidationTestClass(param);

        //when
        Set<ConstraintViolation<EnumValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isNotEmpty();
    }

    @Test
    void Enum_null_허용_테스트() {
        //given
        EnumValidationTestClass testClass = new EnumValidationTestClass(null);

        //when
        Set<ConstraintViolation<EnumValidationTestClass>> validate = validator.validate(testClass);

        //then
        Assertions.assertThat(validate).isNotEmpty();
    }

    static class EnumValidationTestClass {
        @ValidEnum(enumClass = StoreType.class)
        private final String storeType;

        public EnumValidationTestClass(String storeType) {
            this.storeType = storeType;
        }
    }
}
