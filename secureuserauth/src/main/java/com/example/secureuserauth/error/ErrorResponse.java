package com.example.secureuserauth.error;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> fields;

    private ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /* ===== FACTORY METHODS ===== */

    public static ErrorResponse of(
        int status,
        String error,
        String message,
        String path
    ) {
        ErrorResponse response = new ErrorResponse();
        response.status = status;
        response.error = error;
        response.message = message;
        response.path = path;
        return response;
    }

    public static ErrorResponse validation(
        String message,
        String path,
        Map<String, String> fields
    ) {
        ErrorResponse response = new ErrorResponse();
        response.status = 400;
        response.error = "BAD_REQUEST";
        response.message = message;
        response.path = path;
        response.fields = fields;
        return response;
    }
}
