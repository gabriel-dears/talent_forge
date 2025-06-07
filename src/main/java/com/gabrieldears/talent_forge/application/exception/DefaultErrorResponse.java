package com.gabrieldears.talent_forge.application.exception;

import java.time.LocalDateTime;

public record DefaultErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
