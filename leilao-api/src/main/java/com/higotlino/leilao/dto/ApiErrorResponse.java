package com.higotlino.leilao.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ApiErrorResponse {

    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final Map<String, String> details;

    private ApiErrorResponse(String error, String message,
                             String path, Map<String, String> details) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    public static ApiErrorResponse of(String error, String message, String path) {
        return new ApiErrorResponse(error, message, path, null);
    }

    public static ApiErrorResponse of(String error, String message,
                                      String path, Map<String, String> details) {
        return new ApiErrorResponse(error, message, path, details);
    }
}
