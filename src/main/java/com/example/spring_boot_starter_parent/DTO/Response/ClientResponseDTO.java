package com.example.spring_boot_starter_parent.DTO.Response;


import com.example.spring_boot_starter_parent.Model.Policy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ClientResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate dateOfBirth,
        String address,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer policyCount,
        List<PolicySummaryDTO> policies
) {
    public static ClientResponseDTO withoutPolicies(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate dateOfBirth,
            String address,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Integer policyCount
    )
    {
        return new ClientResponseDTO(
                id, firstName, lastName,email,
                phone, dateOfBirth, address, active, createdAt,
                updatedAt,policyCount, null);
    }
}
