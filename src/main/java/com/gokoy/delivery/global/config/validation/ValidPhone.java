package com.gokoy.delivery.global.config.validation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface ValidPhone {
    String message() default "Please follow the format. (XXX-XXXX-XXXX)";

    Class[] groups() default {};

    Class[] payload() default {};
}
