package com.socle.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.socle.repository.IUserRepository;
import com.socle.service.annotation.UniqueUser;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean isValid(String userEmail, ConstraintValidatorContext context) {
        return !userRepository.findByEmail(userEmail).isPresent();
    }

}
