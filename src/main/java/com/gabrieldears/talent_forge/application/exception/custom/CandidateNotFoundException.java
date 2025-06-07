package com.gabrieldears.talent_forge.application.exception.custom;

public class CandidateNotFoundException extends RuntimeException {
    public CandidateNotFoundException(String message) {
        super(message);
    }
}
