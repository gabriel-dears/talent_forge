package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.application.mapper.JobMapper;
import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.model.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCustomJobRepositoryImpl implements CustomJobRepository {

    private final JpaJobRepository jpaJobRepository;
    private final JobMapper jobMapper;

    public JpaCustomJobRepositoryImpl(JpaJobRepository jpaJobRepository, JobMapper jobMapper) {
        this.jpaJobRepository = jpaJobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public com.gabrieldears.talent_forge.model.JobsGet200Response findAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Job> jobsPage = jpaJobRepository.findAll(pageRequest);
        List<JobResponse> jobResponses = jobMapper.fromJobListToJobResponseList(jobsPage.getContent());
        return getJobsGet200Response(jobResponses, jobsPage);
    }

    @Override
    public Job create(Job job) {
        return jpaJobRepository.save(job);
    }

    @Override
    public Optional<Job> findById(String id) {
        return jpaJobRepository.findById(id);
    }

    private static com.gabrieldears.talent_forge.model.JobsGet200Response getJobsGet200Response(List<JobResponse> jobResponses, Page<Job> jobsPage) {
        return new com.gabrieldears.talent_forge.model.JobsGet200Response()
                .content(jobResponses)
                .totalElements((int) jobsPage.getTotalElements())
                .totalPages(jobsPage.getTotalPages())
                .size(jobsPage.getSize())
                .number(jobsPage.getNumber());
    }

}
