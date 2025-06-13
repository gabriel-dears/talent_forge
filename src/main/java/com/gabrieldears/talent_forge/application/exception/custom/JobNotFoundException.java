package com.gabrieldears.talent_forge.application.exception.custom;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String message) {
        super(message);
    }
}
