package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    private final CustomCandidateRepository customCandidateRepository;

    private final CandidateMapper candidateMapper;

    public CandidateService(CustomCandidateRepository customCandidateRepository, CandidateMapper candidateMapper) {
        this.customCandidateRepository = customCandidateRepository;
        this.candidateMapper = candidateMapper;
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse findById(String id) {
        Candidate candidate = customCandidateRepository.findById(id).orElseThrow(() -> new CandidateNotFoundException(String.format("Candidate with id %s not found", id)));
        return candidateMapper.mapFromCandidateToCandidateResponse(candidate);
    }
}
