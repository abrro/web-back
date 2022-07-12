package com.example.demo.filters;

import com.example.demo.entities.UserType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UserTypeAllowedValidator implements ConstraintValidator<UserTypeAllowed, UserType> {
    private UserType[] subset;

    @Override
    public void initialize(UserTypeAllowed constraintAnnotation) {
        this.subset =constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(UserType userType, ConstraintValidatorContext constraintValidatorContext) {
        return userType == null || Arrays.asList(subset).contains(userType);
    }
}
