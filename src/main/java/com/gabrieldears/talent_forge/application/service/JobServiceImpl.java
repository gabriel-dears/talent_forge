package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.domain.service.JobService;
import com.gabrieldears.talent_forge.model.JobsGet200Response;
import org.springframework.stereotype.Component;

@Component
public class JobServiceImpl implements JobService {

    private final CustomJobRepository customJobRepository;

    public JobServiceImpl(CustomJobRepository customJobRepository) {
        this.customJobRepository = customJobRepository;
    }

    @Override
    public JobsGet200Response findAll(Integer page, Integer size) {
        return customJobRepository.findAll(page, size);
    }
}
