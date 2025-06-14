package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.domain.service.JobService;
import com.gabrieldears.talent_forge.model.JobResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;

@RestController
public class JobController implements com.gabrieldears.talent_forge.api.JobsApi {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.JobsGet200Response> jobsGet(
            @Min(0) @Parameter(name = "page", description = "Page number (starts at 0)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(1) @Parameter(name = "size", description = "Number of candidates per page", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(jobService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Void> jobsIdDelete(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String
                    id
    ) {
        jobService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.JobResponse> jobsIdGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String
                    id
    ) {
        return ResponseEntity.ok(jobService.findById(id));
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.JobResponse> jobsIdPut(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String
                    id,
            @Parameter(name = "JobRequest", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.JobRequest
                    jobRequest
    ) {
        return ResponseEntity.ok(jobService.update(jobRequest, id));
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.JobResponse> jobsPost(
            @Parameter(name = "JobRequest", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.JobRequest
                    jobRequest
    ) {
        JobResponse jobResponse = jobService.create(jobRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(jobResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(jobResponse);
    }
}
