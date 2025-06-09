package com.gabrieldears.talent_forge.application.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Reusable utility that validates any DTO using Bean Validation
 * annotations and throws a ConstraintViolationException when invalid.
 */
@Component
public class BeanInputValidationUtils {

    private final Validator validator;

    public BeanInputValidationUtils(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validate and throw if any violation is found.
     */
    public <T> void validate(T target) {
        Set<ConstraintViolation<T>> violations = validator.validate(target);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
