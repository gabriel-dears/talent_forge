package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCustomCandidateRepositoryImpl implements CustomCandidateRepository {

    private final CandidateMapper candidateMapper;
    private final JpaCandidateRepository jpaCandidateRepository;
    private final ObservationRegistry registry;

    public JpaCustomCandidateRepositoryImpl(CandidateMapper candidateMapper, JpaCandidateRepository jpaCandidateRepository, ObservationRegistry registry) {
        this.candidateMapper = candidateMapper;
        this.jpaCandidateRepository = jpaCandidateRepository;
        this.registry = registry;
    }

    @Override
    public Optional<Candidate> findById(String id) {
        return Observation.createNotStarted("candidate.find-by-id", registry)
                .observe(() -> jpaCandidateRepository.findById(id));
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return Boolean.TRUE.equals(Observation.createNotStarted("candidate.email-exists", registry)
                .observe(() -> jpaCandidateRepository.existsByEmail(email)));
    }

    @Override
    public boolean candidateExists(String candidateId) {
        return Boolean.TRUE.equals(Observation.createNotStarted("candidate.exists-by-id", registry)
                .observe(() -> jpaCandidateRepository.existsById(candidateId)));
    }

    @Override
    public Candidate create(Candidate candidate) {
        return Observation.createNotStarted("candidate.create", registry)
                .observe(() -> jpaCandidateRepository.save(candidate));
    }

    @Override
    public void deleteById(String id) {
        Observation.createNotStarted("candidate.delete", registry)
                .observe(() -> {
                    jpaCandidateRepository.deleteById(id);
                    return null;
                });
    }

    @Override
    public com.gabrieldears.talent_forge.model.CandidatesGet200Response findAll(Integer page, Integer size) {
        return Observation.createNotStarted("candidate.find-all", registry)
                .observe(() -> {
                    PageRequest pageRequest = PageRequest.of(page, size);
                    Page<Candidate> candidatesPage = jpaCandidateRepository.findAll(pageRequest);
                    List<com.gabrieldears.talent_forge.model.CandidateResponse> candidateResponseList =
                            candidateMapper.mapFromCandidateListToCandidateResponseList(candidatesPage.getContent());
                    return getCandidatesGet200Response(candidateResponseList, candidatesPage);
                });
    }

    @Override
    public Candidate update(Candidate candidateToBeUpdated) {
        return Observation.createNotStarted("candidate.update", registry)
                .observe(() -> jpaCandidateRepository.save(candidateToBeUpdated));
    }

    @Override
    public boolean emailAlreadyExistsForAnotherCandidate(String email, String id) {
        return Boolean.TRUE.equals(Observation.createNotStarted("candidate.email-exists-other-id", registry)
                .observe(() -> jpaCandidateRepository.existsByEmailAndIdNot(email, id)));
    }

    @Override
    public Page<Candidate> findByDateNotificationLessThanEqual(LocalDate dateNotification, Pageable pageable) {
        return Observation.createNotStarted("candidate.find-by-dateNotification", registry)
                .observe(() -> jpaCandidateRepository.findByDateNotificationLessThanEqual(dateNotification, pageable));
    }

    private static com.gabrieldears.talent_forge.model.CandidatesGet200Response getCandidatesGet200Response(List<com.gabrieldears.talent_forge.model.CandidateResponse> candidateResponseList, Page<Candidate> candidatesPage) {
        com.gabrieldears.talent_forge.model.CandidatesGet200Response response = new com.gabrieldears.talent_forge.model.CandidatesGet200Response();
        response.setContent(candidateResponseList);
        response.setTotalElements((int) candidatesPage.getTotalElements());
        response.setTotalPages(candidatesPage.getTotalPages());
        response.setNumber(candidatesPage.getNumber());
        response.setSize(candidatesPage.getSize());
        return response;
    }
}
