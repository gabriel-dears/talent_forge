package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaJobRepository extends JpaRepository<Job, String> {
}
