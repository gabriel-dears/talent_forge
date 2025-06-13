package com.gabrieldears.talent_forge.application.exception;

import com.gabrieldears.talent_forge.application.exception.custom.CandidateNotFoundException;
import com.gabrieldears.talent_forge.application.exception.custom.EmailAlreadyExistsException;
import com.gabrieldears.talent_forge.application.exception.custom.JobNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CandidateNotFoundException.class, JobNotFoundException.class})
    public ResponseEntity<DefaultErrorResponse> handleCandidateNotFoundException(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, request, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, ex.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class, EmailAlreadyExistsException.class})
    public ResponseEntity<DefaultErrorResponse> handleConstraintViolationException(HttpServletRequest request, Exception ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, request, ex.getMessage());
    }

    private ResponseEntity<DefaultErrorResponse> buildErrorResponse(HttpStatus status, HttpServletRequest request, String message) {
        DefaultErrorResponse defaultErrorResponse = new DefaultErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(defaultErrorResponse, status);
    }

}
