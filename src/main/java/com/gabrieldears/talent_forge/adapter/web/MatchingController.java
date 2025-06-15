package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.adapter.client.AiMatcherClient;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MatchingController implements com.gabrieldears.talent_forge.api.MatchingApi {

    private final AiMatcherClient aiMatcherClient;

    public MatchingController(AiMatcherClient aiMatcherClient) {
        this.aiMatcherClient = aiMatcherClient;
    }

    @Override
    public ResponseEntity<List<com.gabrieldears.talent_forge.model.MatchResult>> candidatesIdMatchesGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(Arrays.asList(aiMatcherClient.getMatchesForCandidate(id)));
    }

    public ResponseEntity<List<com.gabrieldears.talent_forge.model.CandidateResponse>> jobsIdMatchesGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(Arrays.asList(aiMatcherClient.getMatchesForJob(id)));
    }

}
