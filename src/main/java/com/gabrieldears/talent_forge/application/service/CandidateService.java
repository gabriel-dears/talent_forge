package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.application.validator.CreateCandidateValidator;
import com.gabrieldears.talent_forge.application.validator.RetrieveCandidateByIdValidator;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CustomCandidateRepository customCandidateRepository;
    private final CandidateMapper candidateMapper;
    private final RetrieveCandidateByIdValidator retrieveCandidateByIdValidator;
    private final CreateCandidateValidator createCandidateValidator;

    public CandidateService(CustomCandidateRepository customCandidateRepository, CandidateMapper candidateMapper, RetrieveCandidateByIdValidator retrieveCandidateByIdValidator, CreateCandidateValidator createCandidateValidator) {
        this.customCandidateRepository = customCandidateRepository;
        this.candidateMapper = candidateMapper;
        this.retrieveCandidateByIdValidator = retrieveCandidateByIdValidator;
        this.createCandidateValidator = createCandidateValidator;
    }

    public com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size) {
        return customCandidateRepository.findAll(page, size);
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse findById(String id) {
        retrieveCandidateByIdValidator.validate(id);
        Candidate candidate = customCandidateRepository.findById(id).orElseThrow(() -> new CandidateNotFoundException(String.format("Candidate with id %s not found", id)));
        return candidateMapper.mapFromCandidateToCandidateResponse(candidate);
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse create(CandidateRequestDto candidatesPostRequest) {
        createCandidateValidator.validate(candidatesPostRequest);
        Candidate candidate = candidateMapper.mapFromCandidatePostRequestToCandidate(candidatesPostRequest);
        Candidate candidateAfterCreation = customCandidateRepository.create(candidate);
        return candidateMapper.mapFromCandidateToCandidateResponse(candidateAfterCreation);
    }

    public boolean existsById(String id) {
        return customCandidateRepository.candidateExists(id);
    }

    public void delete(String id) {
        if (!existsById(id)) {
            throw new CandidateNotFoundException(String.format("Candidate with id %s not found", id));
        }
        customCandidateRepository.deleteById(id);
    }
}
