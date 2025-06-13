package com.gabrieldears.talent_forge.domain.repository;

import com.gabrieldears.talent_forge.domain.model.Job;

import java.util.Optional;

public interface CustomJobRepository {

    com.gabrieldears.talent_forge.model.JobsGet200Response findAll(Integer page, Integer size);

    Job create(Job job);

    Optional<Job> findById(String id);
}
