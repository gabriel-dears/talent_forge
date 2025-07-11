package com.gabrieldears.talent_forge.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Company extends User implements Serializable {

    private String companyName;

    @OneToMany(mappedBy = "company", orphanRemoval = true)
    private Set<Job> jobs;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }
}
