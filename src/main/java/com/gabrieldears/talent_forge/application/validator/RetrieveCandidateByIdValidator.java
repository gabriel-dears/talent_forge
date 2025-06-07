package com.gabrieldears.talent_forge.application.validator;

import com.gabrieldears.talent_forge.application.exception.custom.InvalidIdException;
import org.springframework.stereotype.Component;

@Component
public class RetrieveCandidateByIdValidator {
    public void validate(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidIdException("The id must not be null or empty!");
        }
    }
}
