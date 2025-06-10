package com.gabrieldears.talent_forge.domain.service;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;

public interface CandidateService {

    com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size);

    com.gabrieldears.talent_forge.model.CandidateResponse findById(String id);

    com.gabrieldears.talent_forge.model.CandidateResponse create(CandidateRequestDto candidatesPostRequest);

    boolean existsById(String id);

    void delete(String id);

    com.gabrieldears.talent_forge.model.CandidateResponse update(CandidateRequestDto candidateRequestDto, String id);

}
