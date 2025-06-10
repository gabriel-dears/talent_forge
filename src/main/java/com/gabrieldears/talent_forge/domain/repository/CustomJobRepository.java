package com.gabrieldears.talent_forge.domain.repository;

public interface CustomJobRepository {

    com.gabrieldears.talent_forge.model.JobsGet200Response findAll(Integer page, Integer size);
}
