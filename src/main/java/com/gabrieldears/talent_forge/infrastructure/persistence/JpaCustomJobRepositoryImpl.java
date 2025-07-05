package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.application.mapper.JobMapper;
import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.model.JobResponse;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCustomJobRepositoryImpl implements CustomJobRepository {

    private final JpaJobRepository jpaJobRepository;
    private final JobMapper jobMapper;
    private final ObservationRegistry registry;

    public JpaCustomJobRepositoryImpl(JpaJobRepository jpaJobRepository, JobMapper jobMapper, ObservationRegistry registry) {
        this.jpaJobRepository = jpaJobRepository;
        this.jobMapper = jobMapper;
        this.registry = registry;
    }

    @Override
    public com.gabrieldears.talent_forge.model.JobsGet200Response findAll(Integer page, Integer size) {
        return Observation.createNotStarted("job.find-all", registry)
                .observe(() -> {
                    PageRequest pageRequest = PageRequest.of(page, size);
                    Page<Job> jobsPage = jpaJobRepository.findAll(pageRequest);
                    List<JobResponse> jobResponses = jobMapper.fromJobListToJobResponseList(jobsPage.getContent());
                    return getJobsGet200Response(jobResponses, jobsPage);
                });
    }

    @Override
    public Job create(Job job) {
        return Observation.createNotStarted("job.create", registry)
                .observe(() -> jpaJobRepository.save(job));
    }

    @Override
    public Optional<Job> findById(String id) {
        return Observation.createNotStarted("job.find-by-id", registry)
                .observe(() -> jpaJobRepository.findById(id));
    }

    @Override
    public void delete(String id) {
        Observation.createNotStarted("job.delete", registry)
                .observe(() -> {
                    jpaJobRepository.deleteById(id);
                    return null;
                });
    }

    @Override
    public boolean existsById(String id) {
        return Boolean.TRUE.equals(Observation.createNotStarted("job.exists-by-id", registry)
                .observe(() -> jpaJobRepository.existsById(id)));
    }

    @Override
    public Job update(Job job) {
        return Observation.createNotStarted("job.update", registry)
                .observe(() -> jpaJobRepository.save(job));
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
