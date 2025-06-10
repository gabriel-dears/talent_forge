package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.application.validator.CreateCandidateValidator;
import com.gabrieldears.talent_forge.application.validator.RetrieveCandidateByIdValidator;
import com.gabrieldears.talent_forge.application.validator.UpdateCandidateValidator;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CustomCandidateRepository customCandidateRepository;
    private final CandidateMapper candidateMapper;
    private final RetrieveCandidateByIdValidator retrieveCandidateByIdValidator;
    private final CreateCandidateValidator createCandidateValidator;
    private final UpdateCandidateValidator updateCandidateValidator;

    public CandidateService(CustomCandidateRepository customCandidateRepository, CandidateMapper candidateMapper, RetrieveCandidateByIdValidator retrieveCandidateByIdValidator, CreateCandidateValidator createCandidateValidator, UpdateCandidateValidator updateCandidateValidator) {
        this.customCandidateRepository = customCandidateRepository;
        this.candidateMapper = candidateMapper;
        this.retrieveCandidateByIdValidator = retrieveCandidateByIdValidator;
        this.createCandidateValidator = createCandidateValidator;
        this.updateCandidateValidator = updateCandidateValidator;
    }

    public com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size) {
        return customCandidateRepository.findAll(page, size);
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse findById(String id) {
        retrieveCandidateByIdValidator.validate(id);
        Candidate candidate = findByByIdFromRepo(id);
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
        verifyCandidateByIdFromRepo(id);
        customCandidateRepository.deleteById(id);
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse update(CandidateRequestDto candidateRequestDto, String id) {
        verifyCandidateByIdFromRepo(id);
        updateCandidateValidator.validate(candidateRequestDto, id);
        Candidate candidateToBeUpdated = candidateMapper.mapFromCandidatePutRequestToCandidate(candidateRequestDto, id);
        Candidate candidateAfterUpdate = customCandidateRepository.update(candidateToBeUpdated);
        return candidateMapper.mapFromCandidateToCandidateResponse(candidateAfterUpdate);
    }

    private void verifyCandidateByIdFromRepo(String id) {
        if (!existsById(id)) {
            throw new CandidateNotFoundException(String.format("Candidate with id %s not found", id));
        }
    }

    private Candidate findByByIdFromRepo(String id) {
        return customCandidateRepository.findById(id).orElseThrow(() -> new CandidateNotFoundException(String.format("Candidate with id %s not found", id)));
    }

}
