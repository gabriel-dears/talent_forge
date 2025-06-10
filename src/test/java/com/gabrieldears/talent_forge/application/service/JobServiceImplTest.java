package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.domain.repository.CustomJobRepository;
import com.gabrieldears.talent_forge.model.JobResponse;
import com.gabrieldears.talent_forge.model.JobsGet200Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private CustomJobRepository customJobRepository;

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
}