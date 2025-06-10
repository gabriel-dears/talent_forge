package com.gabrieldears.talent_forge.application.mapper;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.candidate.ResumeProcessor;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.model.Resume;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    private final ResumeProcessor resumeProcessor;

    public CandidateMapper(ResumeProcessor resumeProcessor) {
        this.resumeProcessor = resumeProcessor;
    }

    public CandidateResponse mapFromCandidateToCandidateResponse(Candidate candidate) {
        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setEmail(candidate.getEmail());
        candidateResponse.setName(candidate.getName());
        candidateResponse.setSkills(candidate.getSkills());
        candidateResponse.setExperienceYears(candidate.getExperienceYears());
        candidateResponse.setId(candidate.getId());
        Resume resume = candidate.getResume();
        if (resume != null) {
            candidateResponse.resumeText(resume.getText());
        }
        return candidateResponse;
    }

    public Candidate mapFromCandidatePostRequestToCandidate(CandidateRequestDto candidateRequestDto) {
        Candidate candidate = new Candidate();
        candidate.setEmail(candidateRequestDto.email());
        candidate.setName(candidateRequestDto.name());
        candidate.setSkills(candidateRequestDto.skills());
        candidate.setExperienceYears(candidateRequestDto.experienceYears());
        resumeProcessor.processResume(candidateRequestDto, candidate);
        return candidate;
    }

    public Candidate mapFromCandidatePutRequestToCandidate(CandidateRequestDto candidateRequestDto, String id) {
        Candidate candidate = new Candidate();
        candidate.setId(id);
        candidate.setEmail(candidateRequestDto.email());
        candidate.setName(candidateRequestDto.name());
        candidate.setSkills(candidateRequestDto.skills());
        candidate.setExperienceYears(candidateRequestDto.experienceYears());
        resumeProcessor.processResume(candidateRequestDto, candidate);
        return candidate;
    }


}
