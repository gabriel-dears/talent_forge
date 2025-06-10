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

    private final CandidateMapper candidateMapper;
    private final JpaCandidateRepository jpaCandidateRepository;

    public JpaCustomCandidateRepositoryImpl(CandidateMapper candidateMapper, JpaCandidateRepository jpaCandidateRepository) {
        this.candidateMapper = candidateMapper;
        this.jpaCandidateRepository = jpaCandidateRepository;
    }

    @Override
    public Optional<Candidate> findById(String id) {
        return jpaCandidateRepository.findById(id);
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return jpaCandidateRepository.existsByEmail(email);
    }

    @Override
    public boolean candidateExists(String candidateId) {
        return jpaCandidateRepository.existsById(candidateId);
    }

    @Override
    public Candidate create(Candidate candidate) {
        return jpaCandidateRepository.save(candidate);
    }

    @Override
    public void deleteById(String id) {
        jpaCandidateRepository.deleteById(id);
    }

    @Override
    public com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Candidate> candidatesPage = jpaCandidateRepository.findAll(pageRequest);
        List<CandidateResponse> candidateResponseList = candidatesPage.getContent().stream().map(candidateMapper::mapFromCandidateToCandidateResponse).toList();
        return getCandidatesGet200Response(candidateResponseList, candidatesPage);
    }

    private static com.gabrieldears.talent_forge.model.CandidatesGet200Response getCandidatesGet200Response(List<CandidateResponse> candidateResponseList, Page<Candidate> candidatesPage) {
        com.gabrieldears.talent_forge.model.CandidatesGet200Response candidatesGet200Response = new com.gabrieldears.talent_forge.model.CandidatesGet200Response();
        candidatesGet200Response.setContent(candidateResponseList);
        candidatesGet200Response.setTotalElements((int) candidatesPage.getTotalElements());
        candidatesGet200Response.setTotalPages(candidatesPage.getTotalPages());
        candidatesGet200Response.setNumber(candidatesPage.getNumber());
        candidatesGet200Response.setSize(candidatesPage.getSize());
        return candidatesGet200Response;
    }

    @Override
    public Candidate update(Candidate candidateToBeUpdated) {
        return jpaCandidateRepository.save(candidateToBeUpdated);
    }

    @Override
    public boolean emailAlreadyExistsForAnotherCandidate(String email, String id) {
        return jpaCandidateRepository.existsByEmailAndIdNot(email, id);
    }
}
