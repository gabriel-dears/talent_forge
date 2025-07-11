package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.repository.CustomUserRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCustomUserRepositoryImpl implements CustomUserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final ObservationRegistry registry;

    public JpaCustomUserRepositoryImpl(JpaUserRepository jpaUserRepository, ObservationRegistry registry) {
        this.jpaUserRepository = jpaUserRepository;
        this.registry = registry;
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return Boolean.TRUE.equals(Observation.createNotStarted("candidate.email-exists", registry)
                .observe(() -> jpaUserRepository.existsByEmail(email)));
    }

    @Override
    public boolean userExists(String userId) {
        return Boolean.TRUE.equals(Observation.createNotStarted("candidate.exists-by-id", registry)
                .observe(() -> jpaUserRepository.existsById(userId)));
    }

    @Override
    public boolean emailAlreadyExistsForAnotherUser(String email, String id) {
        return Boolean.TRUE.equals(Observation.createNotStarted("candidate.email-exists-other-id", registry)
                .observe(() -> jpaUserRepository.existsByEmailAndIdNot(email, id)));
    }

}
