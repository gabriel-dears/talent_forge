package com.gabrieldears.talent_forge.infrastructure.persistence;

import com.gabrieldears.talent_forge.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company, String> {
}
