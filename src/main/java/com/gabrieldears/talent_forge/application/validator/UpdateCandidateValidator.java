package com.gabrieldears.talent_forge.application.validator;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.exception.custom.EmailAlreadyExistsException;
import com.gabrieldears.talent_forge.domain.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UpdateCandidateValidator {

    private final UserService userService;

    public UpdateCandidateValidator(UserService userService) {
        this.userService = userService;
    }

    public void validate(CandidateRequestDto candidateRequestDto, String id) {
        String candidateEmail = candidateRequestDto.email();
        if (emailAlreadyExistsForAnotherCandidate(candidateEmail, id)) {
            throw new EmailAlreadyExistsException(String.format("Email %s is not available", candidateEmail));
        }
    }

    private boolean emailAlreadyExistsForAnotherCandidate(String email, String id) {
        return userService.emailAlreadyExistsForAnotherUser(email, id);
    }
}
