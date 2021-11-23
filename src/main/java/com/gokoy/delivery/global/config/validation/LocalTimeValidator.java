package com.gokoy.delivery.global.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocalTimeValidator implements ConstraintValidator<ValidLocalTime, String> {
    private ValidLocalTime validLocalTime;

    @Override
    public void initialize(ValidLocalTime constraintAnnotation) {
        this.validLocalTime = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
    }
}
