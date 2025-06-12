package com.gabrieldears.talent_forge.application.mapper;

import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.model.JobResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {

    public JobResponse toResponse(Job job) {
        if (job == null) return null;
        return new JobResponse()
                .id(job.getId() != null ? job.getId() : null)
                .title(job.getTitle())
                .description(job.getDescription())
                .requiredSkills(job.getRequiredSkills()) // assuming it's List<String>
                .minExperience(job.getMinExperience());
    }

    public List<JobResponse> toResponseList(List<Job> jobs) {
        return jobs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
