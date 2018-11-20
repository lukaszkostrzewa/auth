package com.lukaszkostrzewa.auth.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.IntPredicate;

/**
 * @author Lukasz Kostrzewa (SG0221165)
 * @since Nov 17, 2018
 */
class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return containsLowerCase(password) && containsUpperCase(password) && containsNumber(password);
    }

    private boolean containsLowerCase(String password) {
        return contains(password, i -> Character.isLetter(i) && Character.isLowerCase(i));
    }

    private boolean containsUpperCase(String password) {
        return contains(password, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

    private boolean containsNumber(String password) {
        return contains(password, Character::isDigit);
    }

    private boolean contains(String password, IntPredicate predicate) {
        return password.chars().anyMatch(predicate);
    }
}
