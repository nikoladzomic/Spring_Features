package com.example.spring_boot_starter_parent.DTO.Response;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ValidationErrorResponseDTO(

        LocalDateTime timestamp,

        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors,
        List<String> errors
) {

    public static ValidationErrorResponseDTO createValidationError (int statusCode, String errorName, String errorMessage, String requestPath) {
        LocalDateTime currentTime = LocalDateTime.now();
        Map<String , String > emptyFieldError = new HashMap<>();
        List<String> emptyErrors = new ArrayList<>();
        return new ValidationErrorResponseDTO(currentTime, statusCode, errorName,errorMessage, requestPath, emptyFieldError, emptyErrors);
    }
}
