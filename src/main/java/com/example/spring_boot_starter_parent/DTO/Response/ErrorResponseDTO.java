package com.example.spring_boot_starter_parent.DTO.Response;


import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record ErrorResponseDTO(

        LocalDateTime timestamp,

        int status,
        String error,
        String message,
        String path
) {

    public static ErrorResponseDTO createError(int statusCode,
                                               String errorName,
                                               String errorMessage,
                                               String requestPath) {
        LocalDateTime currentTime = LocalDateTime.now();
        return new ErrorResponseDTO(currentTime, statusCode, errorName, errorMessage, requestPath);
    }


}
