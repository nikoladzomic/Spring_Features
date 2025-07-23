package com.example.spring_boot_starter_parent.DTO.Response;


import java.time.LocalDateTime;

public record ClientSummaryDTO(

        Long id,
        String firstName,
        String lastName,
        String email,
        Boolean active,
        Integer policyCount,
        LocalDateTime createdAt
) {
    public String fullName()
    {
        return firstName + " " + lastName;
    }
}
