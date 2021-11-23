package com.gokoy.delivery.global.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private ValidPhone validPhone;

    @Override
    public void initialize(ValidPhone constraintAnnotation) {
        this.validPhone = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches("01[016789]-\\d{3,4}-\\d{4}");
    }
}
