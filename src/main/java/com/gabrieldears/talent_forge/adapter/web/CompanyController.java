package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.adapter.web.dto.JwtResponse;
import com.gabrieldears.talent_forge.domain.service.CompanyService;
import com.gabrieldears.talent_forge.infrastructure.security.service.RefreshTokenService;
import com.gabrieldears.talent_forge.model.CandidateRegistrationResponseTokenInfo;
import com.gabrieldears.talent_forge.model.CompanyRegistrationResponse;
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
public class CompanyController implements com.gabrieldears.talent_forge.api.CompaniesApi {

    private final CompanyService companyService;
    private final RefreshTokenService refreshTokenService;

    public CompanyController(CompanyService companyService, RefreshTokenService refreshTokenService) {
        this.companyService = companyService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompaniesGet200Response> companiesGet(
            @Min(0) @Parameter(name = "page", description = "Page number", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(1) @Parameter(name = "size", description = "Page size", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(companyService.findAll(page, size));
    }

    @Override
    public ResponseEntity<Void> companiesIdDelete(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompanyResponse> companiesIdGet(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        // TODO: retrieve jobs related to that company
        return ResponseEntity.ok(companyService.findById(id));
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.CompanyResponse> companiesIdPut(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
            @Parameter(name = "CompanyRequest", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.CompanyRequest companyRequest
    ) {
        return ResponseEntity.ok(companyService.update(companyRequest, id));
    }

    @Override
    public ResponseEntity<CompanyRegistrationResponse> companiesPost(
            @Parameter(name = "CompanyRequest", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.CompanyRequest companyRequest
    ) {
        com.gabrieldears.talent_forge.model.CompanyResponse companyResponse = companyService.create(companyRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(companyResponse.getId()).toUri();
        JwtResponse jwtResponse = refreshTokenService.generateFullTokenStructure(companyRequest.getEmail(), companyRequest.getPassword());

        CompanyRegistrationResponse companyRegistrationResponse = getCompanyRegistrationResponse(companyResponse, jwtResponse);
        return ResponseEntity.created(location).body(companyRegistrationResponse);
    }

    private CompanyRegistrationResponse getCompanyRegistrationResponse(com.gabrieldears.talent_forge.model.CompanyResponse companyResponse, JwtResponse jwtResponse) {
        CompanyRegistrationResponse companyRegistrationResponse = new CompanyRegistrationResponse();
        companyRegistrationResponse.setCompany(companyResponse);
        CandidateRegistrationResponseTokenInfo tokenInfo = new CandidateRegistrationResponseTokenInfo();
        tokenInfo.setAccessToken(jwtResponse.accessToken());
        tokenInfo.setRefreshToken(jwtResponse.refreshToken());
        companyRegistrationResponse.setTokenInfo(tokenInfo);
        return companyRegistrationResponse;
    }

}
