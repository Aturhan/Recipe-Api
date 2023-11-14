package com.abdullahturhan.recipe.controller.validations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;


@Component
public class FoodNameValidator implements ConstraintValidator<FoodNameConstraint,String> {
    @Override
    public void initialize(FoodNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        s = s.replaceAll("[^a-zA-Z0-9]","");
        return true;
    }
}
