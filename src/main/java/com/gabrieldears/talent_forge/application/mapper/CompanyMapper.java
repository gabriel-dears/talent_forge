package com.gabrieldears.talent_forge.application.mapper;

import com.gabrieldears.talent_forge.domain.model.Company;
import com.gabrieldears.talent_forge.model.CompanyRequest;
import com.gabrieldears.talent_forge.model.CompanyResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapper {

    public CompanyResponse mapFromCompanyToCompanyResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setEmail(company.getEmail());
        return companyResponse;
    }

    public List<CompanyResponse> mapFromCompanyListToCompanyResponseList(List<Company> companies) {
        return companies.stream().map(this::mapFromCompanyToCompanyResponse).toList();
    }

    public Company mapFromCompanyPostRequestToCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setEmail(companyRequest.getEmail());
        company.setPassword(companyRequest.getPassword());
        return company;
    }

    public Company mapFromCompanyPutRequestToCompany(CompanyRequest companyRequest, String id) {
        Company company = new Company();
        company.setId(id);
        company.setCompanyName(companyRequest.getCompanyName());
        company.setEmail(companyRequest.getEmail());
        company.setPassword(companyRequest.getPassword());
        return company;
    }

}
