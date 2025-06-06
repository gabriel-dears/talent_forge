package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCustomCandidateRepositoryImpl implements CustomCandidateRepository {

    private final JpaCandidateRepository repository;

    public JpaCustomCandidateRepositoryImpl(JpaCandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Candidate> findById(String id) {
        return repository.findById(id);
    }
}
