package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.mapper.JobMapper;
import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.domain.service.JobService;
import com.gabrieldears.talent_forge.model.JobRequest;
import com.gabrieldears.talent_forge.model.JobResponse;
import com.gabrieldears.talent_forge.model.JobsGet200Response;
import org.springframework.stereotype.Component;

@Component
public class JobServiceImpl implements JobService {

    private final CustomJobRepository customJobRepository;
    private final JobMapper jobMapper;

    public JobServiceImpl(CustomJobRepository customJobRepository, JobMapper jobMapper) {
        this.customJobRepository = customJobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public JobsGet200Response findAll(Integer page, Integer size) {
        return customJobRepository.findAll(page, size);
    }

    @Override
    public JobResponse create(JobRequest jobRequest) {
        Job job = jobMapper.fromJobRequestToJob(jobRequest);
        Job jobAfterCreation = customJobRepository.create(job);
        return jobMapper.fromJobToJobResponse(jobAfterCreation);
    }
}
