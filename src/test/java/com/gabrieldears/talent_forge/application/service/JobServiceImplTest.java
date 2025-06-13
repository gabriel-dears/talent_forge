package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.JobNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.JobMapper;
import com.gabrieldears.talent_forge.domain.model.Job;
import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.model.JobRequest;
import com.gabrieldears.talent_forge.model.JobResponse;
import com.gabrieldears.talent_forge.model.JobsGet200Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private CustomJobRepository customJobRepository;

    @Mock
    JobMapper jobMapper;

    @InjectMocks
    private JobServiceImpl jobService;

    @Test
    void findAll() {
        //Arrange
        JobsGet200Response jobsGet200Response = new JobsGet200Response();
        jobsGet200Response.setContent(List.of(new JobResponse()));
        when(customJobRepository.findAll(anyInt(), anyInt())).thenReturn(jobsGet200Response);
        // Act
        jobService.findAll(1, 1);
        // Assert
        Assertions.assertEquals(1, jobsGet200Response.getContent().size());
        Assertions.assertNotNull(jobsGet200Response.getContent().getFirst());
    }

    @Test
    void shouldNotCreateJobBecauseTitleIsNull() {
        // Arrange
        when(jobMapper.fromJobRequestToJob(isNull())).thenReturn(null);
        doThrow(ConstraintViolationException.class).when(customJobRepository).create(null);
        // Act and Assert
        Assertions.assertThrows(ConstraintViolationException.class, () -> jobService.create(null));
    }

    @Test
    void shouldCreateJob() {
        // Arrange
        JobRequest jobRequest = new JobRequest();
        jobRequest.setTitle("title");
        jobRequest.setDescription("description");
        jobRequest.setMinExperience(3);
        jobRequest.requiredSkills(List.of("skill1", "skill2"));
        Job job = new Job();
        job.setTitle("title");
        job.setDescription("description");
        job.setMinExperience(3);
        job.setRequiredSkills(List.of("skill1", "skill2"));
        JobResponse jobResponse = new JobResponse();
        jobResponse.setTitle("title");
        jobResponse.setDescription("description");
        jobResponse.setMinExperience(3);
        jobResponse.requiredSkills(List.of("skill1", "skill2"));
        when(jobMapper.fromJobRequestToJob(any(JobRequest.class))).thenReturn(job);
        when(jobMapper.fromJobToJobResponse(any(Job.class))).thenReturn(jobResponse);
        when(customJobRepository.create(any())).thenReturn(job);
        // Act
        JobResponse finalJobResponse = jobService.create(jobRequest);
        // Assert
        Assertions.assertNotNull(finalJobResponse);
        Assertions.assertEquals(jobResponse, finalJobResponse);
    }

    @Test
    void shouldNotFindJobById() {
        // Arrange
        when(customJobRepository.findById(anyString())).thenReturn(Optional.empty());
        // Act and Assert
        Assertions.assertThrows(JobNotFoundException.class, () -> jobService.findById("anyUnknownId"));
    }

    @Test
    void shouldFindJobById() {
        // Arrange
        when(jobMapper.fromJobToJobResponse(any(Job.class))).thenReturn(new JobResponse());
        when(customJobRepository.findById(anyString())).thenReturn(Optional.of(new Job()));
        // Act
        JobResponse jobResponse = jobService.findById("anyUnknownId");
        // Assert
        Assertions.assertNotNull(jobResponse);
    }

    @Test
    void shouldNotDeleteJobById() {
        // Arrange
        when(customJobRepository.existsById(anyString())).thenReturn(false);
        // Act and Assert
        Assertions.assertThrows(JobNotFoundException.class, () -> jobService.delete("anyUnknownId"));

    }

}