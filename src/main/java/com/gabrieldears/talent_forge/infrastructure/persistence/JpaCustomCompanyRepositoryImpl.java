package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.application.mapper.CompanyMapper;
import com.gabrieldears.talent_forge.domain.model.Company;
import com.gabrieldears.talent_forge.domain.model.Role;
import com.gabrieldears.talent_forge.domain.repository.CustomCompanyRepository;
import com.gabrieldears.talent_forge.model.CompaniesGet200Response;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCustomCompanyRepositoryImpl implements CustomCompanyRepository {

    private final JpaCompanyRepository jpaCompanyRepository;
    private final ObservationRegistry registry;
    private final CompanyMapper companyMapper;

    public JpaCustomCompanyRepositoryImpl(JpaCompanyRepository jpaCompanyRepository, ObservationRegistry registry, CompanyMapper companyMapper) {
        this.jpaCompanyRepository = jpaCompanyRepository;
        this.registry = registry;
        this.companyMapper = companyMapper;
    }

    @Override
    public Optional<Company> findById(String id) {
        return Observation.createNotStarted("company.find-by-id", registry)
                .observe(() -> jpaCompanyRepository.findById(id));
    }

    @Override
    public Company create(Company company) {
        company.setRole(Role.COMPANY);
        return Observation.createNotStarted("company.create", registry)
                .observe(() -> jpaCompanyRepository.save(company));
    }

    @Override
    public void deleteById(String id) {
        Observation.createNotStarted("company.delete", registry)
                .observe(() -> {
                    jpaCompanyRepository.deleteById(id);
                    return null;
                });
    }

    @Override
    public CompaniesGet200Response findAll(Integer page, Integer size) {
        return Observation.createNotStarted("company.find-all", registry)
                .observe(() -> {
                    PageRequest pageRequest = PageRequest.of(page, size);
                    Page<Company> companyPage = jpaCompanyRepository.findAll(pageRequest);
                    List<com.gabrieldears.talent_forge.model.CompanyResponse> companyResponseList =
                            companyMapper.mapFromCompanyListToCompanyResponseList(companyPage.getContent());
                    return getCandidatesGet200Response(companyResponseList, companyPage);
                });
    }

    @Override
    public Company update(Company companyToBeUpdated) {
        companyToBeUpdated.setRole(Role.COMPANY);
        return Observation.createNotStarted("company.update", registry)
                .observe(() -> jpaCompanyRepository.save(companyToBeUpdated));
    }

    private static com.gabrieldears.talent_forge.model.CompaniesGet200Response getCandidatesGet200Response(List<com.gabrieldears.talent_forge.model.CompanyResponse> companyResponseList, Page<Company> companyPage) {
        com.gabrieldears.talent_forge.model.CompaniesGet200Response response = new com.gabrieldears.talent_forge.model.CompaniesGet200Response();
        response.setContent(companyResponseList);
        response.setTotalElements((int) companyPage.getTotalElements());
        response.setTotalPages(companyPage.getTotalPages());
        response.setNumber(companyPage.getNumber());
        response.setSize(companyPage.getSize());
        return response;
    }

}
