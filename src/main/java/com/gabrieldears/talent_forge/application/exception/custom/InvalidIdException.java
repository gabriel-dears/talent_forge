package com.gabrieldears.talent_forge.application.exception.custom;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message) {
        super(message);
    }
}
