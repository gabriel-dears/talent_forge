package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCandidateRepository extends JpaRepository<Candidate, String> {
    boolean existsByEmail(String email);
}
