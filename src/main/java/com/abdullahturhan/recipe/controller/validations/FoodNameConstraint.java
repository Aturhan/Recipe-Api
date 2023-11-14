package com.abdullahturhan.recipe.controller.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {FoodNameValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FoodNameConstraint {
    String message() default "invalid food name";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

