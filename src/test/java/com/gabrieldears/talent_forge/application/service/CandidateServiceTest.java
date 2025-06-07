package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.exception.custom.EmailAlreadyExistsException;
import com.gabrieldears.talent_forge.application.exception.custom.InvalidIdException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.application.validator.CreateCandidateValidator;
import com.gabrieldears.talent_forge.application.validator.RetrieveCandidateByIdValidator;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.model.Resume;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import com.gabrieldears.talent_forge.model.CandidatesPostRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CustomCandidateRepository customCandidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @Mock
    private RetrieveCandidateByIdValidator retrieveCandidateByIdValidator;

    @Mock
    private CreateCandidateValidator createCandidateValidator;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    void shouldFindCandidateById() {
        // Arrange
        Candidate candidate = new Candidate();
        candidate.setId("1");
        candidate.setEmail("email");
        candidate.setName("name");
        Resume resume = new Resume();
        candidate.setResume(resume);
        candidate.setExperienceYears(10);
        List<String> skills = List.of("skill1", "skill2");
        candidate.setSkills(skills);
        CandidateResponse mockResponse = new CandidateResponse();
        mockResponse.setId("1");
        mockResponse.setEmail("email");
        mockResponse.setName("name");
        mockResponse.setExperienceYears(10);
        mockResponse.setSkills(skills);
        doNothing().when(retrieveCandidateByIdValidator).validate(anyString());
        when(customCandidateRepository.findById(anyString())).thenReturn(Optional.of(candidate));
        when(candidateMapper.mapFromCandidateToCandidateResponse(candidate)).thenReturn(mockResponse);
        // Act
        CandidateResponse candidateResponse = candidateService.findById("1");
        // Assert
        Assertions.assertNotNull(candidateResponse);
        Assertions.assertNotNull(candidateResponse.getId());
        Assertions.assertNotNull(candidateResponse.getEmail());
        Assertions.assertNotNull(candidateResponse.getName());
        Assertions.assertNotNull(candidateResponse.getExperienceYears());
        Assertions.assertNotNull(candidateResponse.getSkills());
        Assertions.assertEquals("1", candidateResponse.getId());
        Assertions.assertEquals("email", candidateResponse.getEmail());
        Assertions.assertEquals("name", candidateResponse.getName());
        Assertions.assertEquals(10, candidateResponse.getExperienceYears());
        Assertions.assertEquals(skills, candidateResponse.getSkills());
        Assertions.assertEquals(resume, candidate.getResume());
    }

    @Test
    void shouldFindAllCandidates() {
    }

    @Test
    void shouldDeleteCandidate() {
    }

    @Test
    void shouldUpdateCandidate() {
    }

    @Test
    void shouldCreateCandidate() {
    }

    @Test
    void shouldNotUpdateCandidate() {
    }

    @Test
    void shouldNotDeleteCandidate() {
    }

    @Test
    void shouldNotCreateCandidateWithExistingEmail() {
        //Arrange
        CandidatesPostRequest candidatesPostRequest = new CandidatesPostRequest();
        candidatesPostRequest.setEmail("email");
        doThrow(EmailAlreadyExistsException.class).when(createCandidateValidator).validate(any(CandidatesPostRequest.class));
        // Act and Assert
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> candidateService.create(candidatesPostRequest));
    }

    @Test
    void shouldNotFindCandidate() {
        // Arrange
        doNothing().when(retrieveCandidateByIdValidator).validate(anyString());
        when(customCandidateRepository.findById(anyString())).thenReturn(Optional.empty());
        // Act and Assert
        Assertions.assertThrows(CandidateNotFoundException.class, () -> candidateService.findById("1"));
    }

    @Test
    void shouldThrowErrorWhenIdIsNullOrEmpty() {
        // Arrange
        doThrow(InvalidIdException.class).when(retrieveCandidateByIdValidator).validate(isNull());
        // Act and Assert
        Assertions.assertThrows(InvalidIdException.class, () -> candidateService.findById(null));
    }

}