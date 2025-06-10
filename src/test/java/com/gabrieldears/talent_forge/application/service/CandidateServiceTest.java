package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.exception.custom.EmailAlreadyExistsException;
import com.gabrieldears.talent_forge.application.exception.custom.InvalidIdException;
import com.gabrieldears.talent_forge.application.mapper.CandidateMapper;
import com.gabrieldears.talent_forge.application.validator.CreateCandidateValidator;
import com.gabrieldears.talent_forge.application.validator.RetrieveCandidateByIdValidator;
import com.gabrieldears.talent_forge.application.validator.UpdateCandidateValidator;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.model.Resume;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import com.gabrieldears.talent_forge.model.CandidatesGet200Response;
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

    @Mock
    private UpdateCandidateValidator updateCandidateValidator;

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
        // Arrange
        Integer page = 0;
        Integer size = 10;
        when(customCandidateRepository.findAll(page, size)).thenReturn(new CandidatesGet200Response());
        // Act
        CandidatesGet200Response findAllCandidatesResponse = candidateService.findAll(page, size);
        // Assert
        Assertions.assertNotNull(findAllCandidatesResponse);
    }

    @Test
    void shouldDeleteCandidate() {
        // Arrange
        when(customCandidateRepository.candidateExists(anyString())).thenReturn(true);
        // Act
        candidateService.delete("anyValidUUID");
        // Assert
        verify(customCandidateRepository, times(1)).candidateExists(anyString());
        verify(customCandidateRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldUpdateCandidate() {
        // Arrange
        CandidateRequestDto candidateRequestDto = mock(CandidateRequestDto.class);
        Candidate candidate = mock(Candidate.class);
        CandidateResponse candidateResponse = mock(CandidateResponse.class);
        when(customCandidateRepository.candidateExists(anyString())).thenReturn(true);
        doNothing().when(updateCandidateValidator).validate(any(CandidateRequestDto.class), anyString());
        when(candidateMapper.mapFromCandidatePutRequestToCandidate(any(CandidateRequestDto.class), anyString())).thenReturn(candidate);
        when(customCandidateRepository.update(any(Candidate.class))).thenReturn(candidate);
        when(candidateMapper.mapFromCandidateToCandidateResponse(any(Candidate.class))).thenReturn(candidateResponse);
        // Act
        CandidateResponse updatedCandidateResponse = candidateService.update(candidateRequestDto, "");
        // Assert
        Assertions.assertNotNull(updatedCandidateResponse);
    }

    @Test
    void shouldCreateCandidate() {
        // Arrange
        CandidateRequestDto candidatesPostRequest = getCandidatesPostRequestForSuccessScenario();
        Candidate candidateMock = getRawCandidateForSuccessScenario();
        CandidateResponse candidateResponseReturnMock = getCandidateResponseForSuccessScenario();
        when(candidateMapper.mapFromCandidatePostRequestToCandidate(candidatesPostRequest)).thenReturn(candidateMock);
        when(customCandidateRepository.create(any(Candidate.class))).thenReturn(candidateMock);
        when(candidateMapper.mapFromCandidateToCandidateResponse(any(Candidate.class))).thenReturn(candidateResponseReturnMock);
        // Act
        CandidateResponse candidateResponse = candidateService.create(candidatesPostRequest);
        // Assert
        Assertions.assertNotNull(candidateResponse);
        Assertions.assertNotNull(candidateResponse.getId());
        Assertions.assertNotNull(candidateResponse.getEmail());
        Assertions.assertNotNull(candidateResponse.getName());
        Assertions.assertNotNull(candidateResponse.getExperienceYears());
        Assertions.assertNotNull(candidateResponse.getSkills());
        Assertions.assertEquals(candidateResponseReturnMock.getId(), candidateResponse.getId());
        Assertions.assertEquals(candidateResponseReturnMock.getEmail(), candidateResponse.getEmail());
        Assertions.assertEquals(candidateResponseReturnMock.getName(), candidateResponse.getName());
        Assertions.assertEquals(10, candidateResponse.getExperienceYears());
    }

    @Test
    void shouldNotUpdateCandidate() {
        // Arrange
        when(customCandidateRepository.candidateExists(anyString())).thenReturn(false);
        // Act and Assert
        Assertions.assertThrows(CandidateNotFoundException.class, () -> candidateService.update(null, ""));
    }

    @Test
    void shouldNotDeleteCandidate() {
        // Arrange
        when(customCandidateRepository.candidateExists(anyString())).thenReturn(false);
        // Act and Assert
        Assertions.assertThrows(CandidateNotFoundException.class, () -> candidateService.delete("1"));
    }

    @Test
    void shouldNotCreateCandidateWithExistingEmail() {
        //Arrange
        CandidateRequestDto candidatesPostRequest = new CandidateRequestDto(
                null,
                "email",
                null,
                null,
                null
        );
        doThrow(EmailAlreadyExistsException.class).when(createCandidateValidator).validate(any(CandidateRequestDto.class));
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

    private static CandidateResponse getCandidateResponseForSuccessScenario() {
        Candidate candidateResponseMock = getRawCandidateForSuccessScenario();
        candidateResponseMock.setId("1");
        CandidateResponse candidateResponseReturnMock = new CandidateResponse();
        candidateResponseReturnMock.setId(candidateResponseMock.getId());
        candidateResponseReturnMock.setEmail(candidateResponseMock.getEmail());
        candidateResponseReturnMock.setName(candidateResponseMock.getName());
        candidateResponseReturnMock.setExperienceYears(candidateResponseMock.getExperienceYears());
        candidateResponseReturnMock.setSkills(candidateResponseMock.getSkills());
        return candidateResponseReturnMock;
    }

    private static Candidate getRawCandidateForSuccessScenario() {
        Candidate candidate = new Candidate();
        candidate.setEmail("email");
        candidate.setName("name");
        Resume resume = new Resume();
        candidate.setResume(resume);
        candidate.setExperienceYears(10);
        candidate.setSkills(List.of("skill1", "skill2"));
        return candidate;
    }

    private static CandidateRequestDto getCandidatesPostRequestForSuccessScenario() {
        return new CandidateRequestDto(
                "name",
                "email",
                10,
                List.of("skill1", "skill2"),
                null
        );
    }

}