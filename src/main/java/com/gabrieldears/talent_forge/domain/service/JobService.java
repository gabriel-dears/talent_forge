package com.gabrieldears.talent_forge.domain.service;

import com.gabrieldears.talent_forge.model.JobsGet200Response;

public interface JobService {

    JobsGet200Response findAll(Integer page, Integer size);

}
