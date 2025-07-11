package com.gabrieldears.talent_forge.application.validator;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.exception.custom.EmailAlreadyExistsException;
import com.gabrieldears.talent_forge.domain.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateCandidateValidator {

    private final UserService userService;

    public CreateCandidateValidator(UserService userService) {
        this.userService = userService;
    }

    public void validate(CandidateRequestDto candidateRequestDto) {
        String candidateEmail = candidateRequestDto.email();
        if (emailAlreadyExists(candidateEmail)) {
            throw new EmailAlreadyExistsException(String.format("Candidate with email %s already exists", candidateEmail));
        }
    }

    private boolean emailAlreadyExists(String email) {
        return userService.emailAlreadyExists(email);
    }
}
