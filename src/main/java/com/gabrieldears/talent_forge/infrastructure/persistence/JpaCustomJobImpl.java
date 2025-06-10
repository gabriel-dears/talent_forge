package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCustomJobImpl implements CustomJobRepository {

    private final JpaJobRepository jpaJobRepository;

    public JpaCustomJobImpl(JpaJobRepository jpaJobRepository) {
        this.jpaJobRepository = jpaJobRepository;
    }

    @Override
    public com.gabrieldears.talent_forge.model.JobsGet200Response findAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Job> jobsPage = jpaJobRepository.findAll(pageRequest);
        // TODO: create mapper and populate JobsGet200Response object correctly
        return null;
    }

}
