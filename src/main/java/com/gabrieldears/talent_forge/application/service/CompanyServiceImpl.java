package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.CompanyNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.CompanyMapper;
import com.gabrieldears.talent_forge.domain.model.Company;
import com.gabrieldears.talent_forge.domain.repository.CustomCompanyRepository;
import com.gabrieldears.talent_forge.domain.service.CompanyService;
import com.gabrieldears.talent_forge.domain.service.UserService;
import com.gabrieldears.talent_forge.model.CompaniesGet200Response;
import com.gabrieldears.talent_forge.model.CompanyRequest;
import com.gabrieldears.talent_forge.model.CompanyResponse;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CustomCompanyRepository customCompanyRepository;
    private final CompanyMapper companyMapper;
    private final UserService userService;

    public CompanyServiceImpl(CustomCompanyRepository customCompanyRepository, CompanyMapper companyMapper, UserService userService) {
        this.customCompanyRepository = customCompanyRepository;
        this.companyMapper = companyMapper;
        this.userService = userService;
    }

    @Override
    public CompaniesGet200Response findAll(Integer page, Integer size) {
        return customCompanyRepository.findAll(page, size);
    }

    @Override
    public CompanyResponse create(CompanyRequest companyRequest) {
        Company company = companyMapper.mapFromCompanyPostRequestToCompany(companyRequest);
        Company createdCompany = customCompanyRepository.create(company);
        return companyMapper.mapFromCompanyToCompanyResponse(createdCompany);
    }

    @Override
    public CompanyResponse findById(String id) {
        return companyMapper.mapFromCompanyToCompanyResponse(customCompanyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company with id " + id + " not found")));
    }

    @Override
    public void delete(String id) {
        verifyCompanyByIdFromRepo(id);
        customCompanyRepository.deleteById(id);
    }

    @Override
    public CompanyResponse update(CompanyRequest companyRequest, String id) {
        verifyCompanyByIdFromRepo(id);
        Company companyToBeUpdated = companyMapper.mapFromCompanyPutRequestToCompany(companyRequest, id);
        Company updatedCompany = customCompanyRepository.update(companyToBeUpdated);
        return companyMapper.mapFromCompanyToCompanyResponse(updatedCompany);
    }

    private void verifyCompanyByIdFromRepo(String id) {
        if (!existsById(id)) {
            throw new CompanyNotFoundException(String.format("Company with id %s not found", id));
        }
    }

    private boolean existsById(String id) {
        return userService.userExists(id);
    }

}
