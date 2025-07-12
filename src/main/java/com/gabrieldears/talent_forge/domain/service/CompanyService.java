package com.gabrieldears.talent_forge.domain.service;

public interface CompanyService {

    com.gabrieldears.talent_forge.model.CompaniesGet200Response findAll(Integer page, Integer size);

    com.gabrieldears.talent_forge.model.CompanyResponse create(com.gabrieldears.talent_forge.model.CompanyRequest companyRequest);

    com.gabrieldears.talent_forge.model.CompanyResponse findById(String id);

    void delete(String id);

    com.gabrieldears.talent_forge.model.CompanyResponse update(com.gabrieldears.talent_forge.model.CompanyRequest companyRequest, String id);

}
