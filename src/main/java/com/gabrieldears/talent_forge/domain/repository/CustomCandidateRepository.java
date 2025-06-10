package com.gabrieldears.talent_forge.domain.repository;

import com.gabrieldears.talent_forge.domain.model.Candidate;

import java.util.Optional;

public interface CustomCandidateRepository {

    Optional<Candidate> findById(String id);

    boolean emailAlreadyExists(String email);

    boolean candidateExists(String candidateId);

    Candidate create(Candidate candidate);

    void deleteById(String id);

    com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size);

    Candidate update(Candidate candidateToBeUpdated);

    boolean emailAlreadyExistsForAnotherCandidate(String email, String id);
}
