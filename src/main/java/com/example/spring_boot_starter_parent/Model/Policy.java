package com.example.spring_boot_starter_parent.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "policies")
@EntityListeners(AuditingEntityListener.class)
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Policy number is required")
    @Size(min = 5, max = 35, message = "Policy number must be between 5 and 35 characters")
    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;

    @NotBlank(message = "Policy type is required")
    @Column(name = "policy_type", nullable = false)
    private String policyType;

    @NotNull(message = "Premium account is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Premium account must be greater than 0.00")
    @Column( name = "premium_account", nullable = false)
    private BigDecimal premiumAmount;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status = PolicyStatus.ACTIVE;

    @Column(name = "description")
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public enum PolicyType {
        LIFE_INSURANCE("Life Insurance"),
        HEALTH_INSURANCE("Health Insurance"),
        AUTO_INSURANCE("Auto Insurance"),
        HOME_INSURANCE("Home Insurance"),
        TRAVEL_INSURANCE("Travel Insurance");

        private final String displayName;

        PolicyType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PolicyStatus {
        ACTIVE("Active"),
        EXPIRED("Expired"),
        CANCELLED("Cancelled"),
        PENDING("Pending"),
        SUSPENDED("Suspended");

        private final String displayName;

        PolicyStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

    }

    public Policy() {}

    public Policy(String policyNumber, String policyType, BigDecimal premiumAmount, LocalDate startDate, LocalDate endDate, Client client) {
        this.policyNumber = policyNumber;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public BigDecimal premiumAmount() {
        return premiumAmount;
    }

    public void setpremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //Business methods

    public Boolean isActive() {
        return active != null && active && status == PolicyStatus.ACTIVE;
    }

    public Boolean isExpired() {
        return endDate.isBefore(LocalDate.now());
    }

    public long getDaysUntilExpiry() {
        return LocalDate.now().until(endDate).getDays();
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", policyNumber='" + policyNumber + '\'' +
                ", policyType='" + policyType + '\'' +
                ", premiumAmount=" + premiumAmount +
                ", status=" + status +
                '}';
    }
}
