package com.gabrieldears.talent_forge.domain.repository;

import com.gabrieldears.talent_forge.domain.model.Candidate;

import java.util.Optional;

public interface CustomCandidateRepository {

    Optional<Candidate> findById(String id);

    boolean emailAlreadyExists(String email);

    Candidate create(Candidate candidate);
}
