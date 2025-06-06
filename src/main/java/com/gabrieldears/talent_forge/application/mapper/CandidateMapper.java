package com.gabrieldears.talent_forge.application.mapper;

import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {


    public CandidateResponse mapFromCandidateToCandidateResponse(Candidate candidate) {
        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setEmail(candidate.getEmail());
        candidateResponse.setName(candidate.getName());
        candidateResponse.setSkills(candidate.getSkills());
        candidateResponse.setExperienceYears(candidate.getExperienceYears());
        candidateResponse.setId(candidate.getId());
        return candidateResponse;
    }
}
