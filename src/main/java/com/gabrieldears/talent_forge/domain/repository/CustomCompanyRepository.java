package com.gabrieldears.talent_forge.domain.repository;

import com.gabrieldears.talent_forge.domain.model.Company;

import java.util.Optional;

public interface CustomCompanyRepository {

    Optional<Company> findById(String id);

    Company create(Company company);

    void deleteById(String id);

    com.gabrieldears.talent_forge.model.CompaniesGet200Response findAll(Integer page, Integer size);

    Company update(Company companyToBeUpdated);

}
