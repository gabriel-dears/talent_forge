package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.application.service.CandidateService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public class CandidateController implements com.gabrieldears.talent_forge.api.CandidatesApi {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CandidateResponse> candidatesIdGet(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return null;
    }
}
