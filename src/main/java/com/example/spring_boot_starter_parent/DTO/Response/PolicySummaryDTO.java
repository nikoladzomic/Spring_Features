package com.example.spring_boot_starter_parent.DTO.Response;


import java.math.BigDecimal;
import java.time.LocalDate;

public record PolicySummaryDTO(

        Long id,
        String policyNumber,
        String policyType,
        BigDecimal premiumAmount,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        Boolean active
) {}
