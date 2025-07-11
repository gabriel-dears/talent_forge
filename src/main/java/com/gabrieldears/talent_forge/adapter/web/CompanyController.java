package com.gabrieldears.talent_forge.adapter.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
public class CompanyController implements com.gabrieldears.talent_forge.api.CompaniesApi {
    
    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompaniesGet200Response> companiesGet(
            @Min(0) @Parameter(name = "page", description = "Page number", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(1) @Parameter(name = "size", description = "Page size", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> companiesIdDelete(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompanyResponse> companiesIdGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompanyResponse> companiesIdPut(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
            @Parameter(name = "CompanyRequest", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.CompanyRequest companyRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompanyResponse> companiesPost(
            @Parameter(name = "CompanyRequest", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.CompanyRequest companyRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
}
