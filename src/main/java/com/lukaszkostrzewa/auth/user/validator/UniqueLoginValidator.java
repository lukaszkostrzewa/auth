package com.lukaszkostrzewa.auth.user.validator;

import com.lukaszkostrzewa.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Lukasz Kostrzewa (SG0221165)
 * @since Nov 19, 2018
 */
@RequiredArgsConstructor
class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        return userRepository.findFirstByLogin(login).isEmpty();
    }
}
