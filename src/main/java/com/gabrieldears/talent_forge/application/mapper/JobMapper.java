package com.gabrieldears.talent_forge.application.mapper;

import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.model.JobRequest;
import com.gabrieldears.talent_forge.model.JobResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {

    public JobResponse fromJobToJobResponse(Job job) {
        if (job == null) return null;
        return new JobResponse()
                .id(job.getId() != null ? job.getId() : null)
                .title(job.getTitle())
                .description(job.getDescription())
                .requiredSkills(job.getRequiredSkills()) // assuming it's List<String>
                .minExperience(job.getMinExperience());
    }

    public List<JobResponse> fromJobListToJobResponseList(List<Job> jobs) {
        return jobs.stream()
                .map(this::fromJobToJobResponse)
                .collect(Collectors.toList());
    }

    public Job fromJobRequestToJob(JobRequest jobRequest) {
        if (jobRequest == null) return null;
        Job job = new Job();
        job.setDescription(jobRequest.getDescription());
        job.setTitle(jobRequest.getTitle());
        job.setMinExperience(jobRequest.getMinExperience());
        job.setRequiredSkills(jobRequest.getRequiredSkills());
        return job;
    }

    public Job fromJobRequestUpdateToJob(com.gabrieldears.talent_forge.model.JobRequest jobRequest, String id) {
        Job job = new Job();
        job.setId(id);
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setMinExperience(jobRequest.getMinExperience());
        job.setRequiredSkills(jobRequest.getRequiredSkills());
        return job;
    }
}
