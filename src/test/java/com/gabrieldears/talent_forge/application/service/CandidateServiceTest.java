package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.model.Resume;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CustomCandidateRepository customCandidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

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
    void shouldNotCreateCandidate() {
    }

    @Test
    void shouldNotFindCandidate() {
        when(customCandidateRepository.findById(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(CandidateNotFoundException.class, () -> candidateService.findById("1"));
    }

}