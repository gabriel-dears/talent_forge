package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.JobNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.JobMapper;
import com.gabrieldears.talent_forge.domain.model.Company;
import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.domain.service.CompanyService;
import com.gabrieldears.talent_forge.domain.service.JobService;
import com.gabrieldears.talent_forge.model.CompanyResponse;
import com.gabrieldears.talent_forge.model.JobRequest;
import com.gabrieldears.talent_forge.model.JobResponse;
import com.gabrieldears.talent_forge.model.JobsGet200Response;
import org.springframework.stereotype.Component;

@Component
public class JobServiceImpl implements JobService {

    private final CustomJobRepository customJobRepository;
    private final JobMapper jobMapper;
    private final CompanyService companyService;

    public JobServiceImpl(CustomJobRepository customJobRepository, JobMapper jobMapper, CompanyService companyService) {
        this.customJobRepository = customJobRepository;
        this.jobMapper = jobMapper;
        this.companyService = companyService;
    }

    @Override
    public JobsGet200Response findAll(Integer page, Integer size) {
        return customJobRepository.findAll(page, size);
    }

    @Override
    public JobResponse create(JobRequest jobRequest) {
        CompanyResponse companyDto = companyService.findById(jobRequest.getCompanyId());
        Job job = jobMapper.fromJobRequestToJob(jobRequest);
        job.setCompany(getCompanyWithOnlyIdPopulated(companyDto));
        Job jobAfterCreation = customJobRepository.create(job);
        return jobMapper.fromJobToJobResponse(jobAfterCreation);
    }

    private Company getCompanyWithOnlyIdPopulated(CompanyResponse companyDto) {
        Company company = new Company();
        company.setId(companyDto.getId());
        return company;
    }

    @Override
    public JobResponse findById(String id) {
        Job job = customJobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(String.format("Job with id %s not found", id)));
        return jobMapper.fromJobToJobResponse(job);
    }

    @Override
    public void delete(String id) {
        if (jobDoesntExist(id)) {
            throw new JobNotFoundException(String.format("Job with id %s not found", id));
        }
        customJobRepository.delete(id);
    }

    @Override
    public JobResponse update(JobRequest jobRequest, String id) {
        Job job = jobMapper.fromJobRequestUpdateToJob(jobRequest, id);
        Job updatedJob = customJobRepository.update(job);
        return jobMapper.fromJobToJobResponse(updatedJob);
    }

    private boolean jobDoesntExist(String id) {
        return !customJobRepository.existsById(id);
    }

}
