package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCustomCandidateRepositoryImpl implements CustomCandidateRepository {

    private final JpaCandidateRepository repository;
    private final CandidateMapper candidateMapper;

    public JpaCustomCandidateRepositoryImpl(JpaCandidateRepository repository, CandidateMapper candidateMapper) {
        this.repository = repository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public Optional<Candidate> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean candidateExists(String candidateId) {
        return repository.existsById(candidateId);
    }

    @Override
    public Candidate create(Candidate candidate) {
        return repository.save(candidate);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Candidate> candidatesPage = repository.findAll(pageRequest);
        List<CandidateResponse> candidateResponseList = candidatesPage.getContent().stream().map(candidateMapper::mapFromCandidateToCandidateResponse).toList();
        com.gabrieldears.talent_forge.model.CandidatesGet200Response candidatesGet200Response = new com.gabrieldears.talent_forge.model.CandidatesGet200Response();
        candidatesGet200Response.setContent(candidateResponseList);
        candidatesGet200Response.setTotalElements((int) candidatesPage.getTotalElements());
        candidatesGet200Response.setTotalPages(candidatesPage.getTotalPages());
        candidatesGet200Response.setNumber(candidatesPage.getNumber());
        candidatesGet200Response.setSize(candidatesPage.getSize());
        return candidatesGet200Response;
    }
}
