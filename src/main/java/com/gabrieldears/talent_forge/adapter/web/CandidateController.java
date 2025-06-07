package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.application.service.CandidateService;
import com.gabrieldears.talent_forge.model.CandidateResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Validated
@RestController
public class CandidateController implements com.gabrieldears.talent_forge.api.CandidatesApi {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public ResponseEntity<CandidateResponse> candidatesIdGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        CandidateResponse candidateResponse = candidateService.findById(id);
        return ResponseEntity.ok(candidateResponse);
    }

    @Override
    public ResponseEntity<CandidateResponse> candidatesPost(
            @Valid @RequestBody com.gabrieldears.talent_forge.model.CandidatesPostRequest candidatesPostRequest
    ) {
        CandidateResponse candidateResponse = candidateService.create(candidatesPostRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(candidateResponse.getId()).toUri();
        return ResponseEntity.created(location).body(candidateResponse);
    }

}
