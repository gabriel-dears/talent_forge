package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JpaCandidateRepository extends JpaRepository<Candidate, String> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String id);

    Page<Candidate> findByDateNotificationLessThanEqual(LocalDate dateNotification, Pageable pageable);
}
