package com.example.spring_boot_starter_parent.Repository;

import com.example.spring_boot_starter_parent.Model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Client> findByActiveTrue();

    List<Client> findByActiveFalse();

    @Query("SELECT c FROM Client c WHERE c.active = true AND " +
            "(LOWER(c.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Client> findActiveClientsBySearchTerm(@Param("searchTerm") String searchTerm);

    Page<Client>findByActiveTrue(Pageable pageable);

    Page<Client>findByActiveFalse(Pageable pageable);

    @Query("SELECT c FROM CLIENT c WHERE c.active = :active AND c.createdAt > :fromDate")
    List<Client> findClientByActiveStatusAndCreatedAfter(
            @Param("active") Boolean active,
            @Param("fromDate") Long fromDate
    );

    @Query("SELECT DISTINCT c FROM Client c \" +\n" +
            "           \"LEFT JOIN FETCH c.policies p \" +\n" +
            "           \"WHERE c.active = true AND c.id = :clientId")
    Optional<Client> findActiveClientWithPolicies(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.active = true")
    Long countActiveClients();

    @Query("SELECT COUNT(c) FROM Client c WHERE c.active = true AND c.createdAt >= :fromDate")
    Long countActiveClientsCreatedAfter(@Param("fromDate") LocalDateTime fromDate);


    @Query(value = "SELECT c.* FROM clients c " +
            "WHERE c.active = 1 " +
            "AND EXISTS (SELECT 1 FROM policies p WHERE p.client_id = c.id AND p.status = 'ACTIVE') " +
            "ORDER BY c.created_at DESC",
            nativeQuery = true)
    List<Client> findActiveClientsWithActivePolicies();
}
