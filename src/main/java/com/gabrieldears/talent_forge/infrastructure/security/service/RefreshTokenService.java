package com.gabrieldears.talent_forge.infrastructure.security.service;

import com.gabrieldears.talent_forge.adapter.web.dto.JwtResponse;
import com.gabrieldears.talent_forge.infrastructure.security.JwtTokenProvider;
import com.gabrieldears.talent_forge.infrastructure.security.model.RefreshToken;
import com.gabrieldears.talent_forge.infrastructure.security.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.refreshExpirationMs}")
    private long refreshTokenDurationMs;

    public RefreshTokenService(RefreshTokenRepository repo, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.refreshTokenRepository = repo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public RefreshToken createRefreshToken(String username) {
        RefreshToken token = new RefreshToken();
        token.setUsername(username);
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(token);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    @Transactional
    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public JwtResponse generateRefreshToken(String token) {

        var refreshTokenResult = findByToken(token)
                .map(refreshToken -> {
                    if (isExpired(refreshToken)) {
                        deleteByToken(token);
                        throw new RuntimeException("Refresh token expired");
                    }

                    String username = refreshToken.getUsername();
                    String newAccessToken = jwtTokenProvider.generateTokenWithUsername(username);

                    return new JwtResponse(newAccessToken, token);
                });

        if (refreshTokenResult.isEmpty()) {
            throw new RuntimeException("Refresh token not found");
        }

        return refreshTokenResult.get();
    }

    public JwtResponse generateFullTokenStructure(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String accessToken = jwtTokenProvider.generateToken(authentication);
            RefreshToken refreshToken = createRefreshToken(email);

            return new JwtResponse(accessToken, refreshToken.getToken());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }


}
