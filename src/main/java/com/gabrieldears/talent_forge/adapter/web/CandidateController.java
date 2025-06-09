package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.application.service.CandidateService;
import com.gabrieldears.talent_forge.application.validator.BeanInputValidationUtils;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
public class CandidateController implements com.gabrieldears.talent_forge.api.CandidatesApi {

    private final CandidateService candidateService;
    private final BeanInputValidationUtils beanInputValidationUtils;

    public CandidateController(CandidateService candidateService, BeanInputValidationUtils beanInputValidationUtils) {
        this.candidateService = candidateService;
        this.beanInputValidationUtils = beanInputValidationUtils;
    }

    @Override
    public ResponseEntity<CandidateResponse> candidatesIdGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        CandidateResponse candidateResponse = candidateService.findById(id);
        return ResponseEntity.ok(candidateResponse);
    }

    @Override
    @PostMapping(value = "/candidates", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CandidateResponse> candidatesPost(
            @Parameter(name = "name") @Valid @RequestParam(value = "name", required = false) String name,
            @Parameter(name = "email") @Valid @RequestParam(value = "email", required = false) String email,
            @Parameter(name = "skills") @Valid @RequestParam(value = "skills", required = false) List<String> skills,
            @Parameter(name = "experienceYears") @Valid @RequestParam(value = "experienceYears", required = false) Integer experienceYears,
            @Parameter(name = "resume") @RequestPart(value = "resume", required = false) MultipartFile resume
    ) {
        CandidateRequestDto candidateRequestDto = new CandidateRequestDto(
                name,
                email,
                experienceYears,
                skills,
                resume
        );
        beanInputValidationUtils.validate(candidateRequestDto);
        CandidateResponse candidateResponse = candidateService.create(candidateRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(candidateResponse.getId()).toUri();
        return ResponseEntity.created(location).body(candidateResponse);
    }

}
