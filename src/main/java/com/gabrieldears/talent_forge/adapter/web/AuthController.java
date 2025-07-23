package com.gabrieldears.talent_forge.adapter.web;

import com.gabrieldears.talent_forge.adapter.web.dto.JwtResponse;
import com.gabrieldears.talent_forge.infrastructure.security.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController implements com.gabrieldears.talent_forge.api.AuthenticationApi {

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(
            RefreshTokenService refreshTokenService
    ) {
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.JwtResponse> loginPost(
            @Parameter(name = "LoginRequest", description = "", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.LoginRequest loginRequest
    ) {

        JwtResponse jwtResponse = refreshTokenService.generateFullTokenStructure(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.ok(getGeneratedTokenResponseDTO(jwtResponse));
    }

    @Override
    public ResponseEntity<com.gabrieldears.talent_forge.model.JwtResponse> refreshPost(
            @Parameter(name = "TokenRefreshRequest", description = "", required = true) @Valid @RequestBody com.gabrieldears.talent_forge.model.TokenRefreshRequest tokenRefreshRequest
    ) {
        String token = tokenRefreshRequest.getRefreshToken();
        JwtResponse jwtResponse = refreshTokenService.generateRefreshToken(token);
        return ResponseEntity.ok(getGeneratedTokenResponseDTO(jwtResponse));
    }

    private com.gabrieldears.talent_forge.model.JwtResponse getGeneratedTokenResponseDTO(JwtResponse jwtResponse) {
        com.gabrieldears.talent_forge.model.JwtResponse generatedTokenResponseDTO = new com.gabrieldears.talent_forge.model.JwtResponse();
        generatedTokenResponseDTO.setAccessToken(jwtResponse.accessToken());
        generatedTokenResponseDTO.setRefreshToken(jwtResponse.refreshToken());
        return generatedTokenResponseDTO;
    }

}
