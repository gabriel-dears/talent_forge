package com.gabrieldears.talent_forge.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Candidate extends User implements Serializable {

    @NotBlank
    private String name;

    @Embedded
    private Resume resume;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> skills;

    @Min(0)
    @NotNull
    private int experienceYears;

    @Column(nullable = false)
    private LocalDate dateNotification;

    @Override
    public void prePersist() {
        super.prePersist();
        if (dateNotification == null) {
            dateNotification = LocalDate.now();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public LocalDate getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(LocalDate dateNotification) {
        this.dateNotification = dateNotification;
    }
}
