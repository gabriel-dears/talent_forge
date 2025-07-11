package com.gabrieldears.talent_forge.application.exception.custom;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
