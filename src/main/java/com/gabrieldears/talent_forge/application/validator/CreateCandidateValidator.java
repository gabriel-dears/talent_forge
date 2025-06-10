package com.gabrieldears.talent_forge.application.validator;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.exception.custom.EmailAlreadyExistsException;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateCandidateValidator {

    private final CustomCandidateRepository customCandidateRepository;

    public CreateCandidateValidator(CustomCandidateRepository customCandidateRepository) {
        this.customCandidateRepository = customCandidateRepository;
    }

    public void validate(CandidateRequestDto candidateRequestDto) {
        String candidateEmail = candidateRequestDto.email();
        if (emailAlreadyExists(candidateEmail)) {
            throw new EmailAlreadyExistsException(String.format("Candidate with email %s already exists", candidateEmail));
        }
    }

    private boolean emailAlreadyExists(String email) {
        return customCandidateRepository.emailAlreadyExists(email);
    }
}
