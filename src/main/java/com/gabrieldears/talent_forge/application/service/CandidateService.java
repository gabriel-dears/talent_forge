package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.application.validator.RetrieveCandidateByIdValidator;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CustomCandidateRepository customCandidateRepository;
    private final CandidateMapper candidateMapper;
    private final RetrieveCandidateByIdValidator retrieveCandidateByIdValidator;

    public CandidateService(CustomCandidateRepository customCandidateRepository, CandidateMapper candidateMapper, RetrieveCandidateByIdValidator retrieveCandidateByIdValidator) {
        this.customCandidateRepository = customCandidateRepository;
        this.candidateMapper = candidateMapper;
        this.retrieveCandidateByIdValidator = retrieveCandidateByIdValidator;
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse findById(String id) {
        retrieveCandidateByIdValidator.validate(id);
        Candidate candidate = customCandidateRepository.findById(id).orElseThrow(() -> new CandidateNotFoundException(String.format("Candidate with id %s not found", id)));
        return candidateMapper.mapFromCandidateToCandidateResponse(candidate);
    }
}
