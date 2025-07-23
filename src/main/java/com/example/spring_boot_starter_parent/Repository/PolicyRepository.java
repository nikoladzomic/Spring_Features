package com.example.spring_boot_starter_parent.Repository;


import com.example.spring_boot_starter_parent.Model.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    
    Optional<Policy> findByPolicyNumber(String policyNumber);
    
    boolean existsByPolicyNumber(String policyNumber);
    
    List<Policy> findByActiveTrue();
    
    List<Policy> findByStatus(Policy.PolicyStatus status);
    
    List<Policy> findByType(Policy.PolicyType type);
    
    List<Policy> findByClientIdAndActiveTrue(Long clientId);
    
    List<Policy> findByClientIdAndStatus(Long clientId, Policy.PolicyStatus status);

    @Query("SELECT p FROM Policy p WHERE p.active = true AND p.endDate <= :date")
    List<Policy> findActivePoliciesExpiringBefore(@Param("date") LocalDate date);

    @Query("SELECT p FROM Policy p WHERE p.active= true AND p.endDate BETWEEN :from AND :to")
    List<Policy> findActivePoliciesExpiringBetween(
            @Param("from") LocalDate startDate,
            @Param("to") LocalDate endDate
    );

    @Query("SELECT SUM (p.premiumAmount) FROM Policy p WHERE p.active = true AND p.status = 'ACTIVE'")
    BigDecimal calculateTotalActivePremiums();
    
    @Query("SELECT SUM (p.premiumAmount) FROM Policy p WHERE p.client.id = :clientId AND p.active = true")
    BigDecimal calculateTotalPremiums(@Param("clientId") Long clientId);
    
    @Query("SELECT p FROM Policy p WHERE p.active = true ORDER BY p.createdAt DESC")
    Page<Policy> findRecentActivePolicies(Pageable pageable);


}
