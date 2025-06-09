package com.gabrieldears.talent_forge.adapter.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CandidateRequestDto(
        @NotBlank(message = "The 'name' field is mandatory")
        String name,
        @NotBlank(message = "The 'email' field is mandatory")
        @Email
        String email,
        @NotNull
        Integer experienceYears,
        @NotEmpty
        List<String> skills,
        MultipartFile resume
) {
}
