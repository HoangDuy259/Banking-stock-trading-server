package com.example.demo.validator;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {TradingOrderValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrder {
    String message() default "Price is invalid, please try again with a price between floor price and ceil price ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
