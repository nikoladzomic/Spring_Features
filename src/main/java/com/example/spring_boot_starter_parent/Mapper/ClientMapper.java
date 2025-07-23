package com.example.spring_boot_starter_parent.Mapper;

import com.example.spring_boot_starter_parent.DTO.Request.CreateClientDTO;
import com.example.spring_boot_starter_parent.DTO.Request.UpdateClientDTO;
import com.example.spring_boot_starter_parent.DTO.Response.ClientResponseDTO;
import com.example.spring_boot_starter_parent.DTO.Response.ClientSummaryDTO;
import com.example.spring_boot_starter_parent.DTO.Response.PolicySummaryDTO;
import com.example.spring_boot_starter_parent.Model.Client;
import com.example.spring_boot_starter_parent.Model.Policy;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "policies", ignore = true)
    Client toEntity(CreateClientDTO createClientDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "policies", ignore = true)
    void updateEntityFromDTO(UpdateClientDTO updateClientDTO, @MappingTarget Client client);

    @Mapping(target = "policyCount", expression = "java(getPolicyCount(client))")
    @Mapping(target = "policies" , ignore = true)
    ClientResponseDTO toResponseDTO(Client client);

    @Mapping(target = "policyCount", expression = "java(getPolicyCount(client))")
    @Mapping(target = "policies", source = "policies")
    ClientResponseDTO toResponseDTOWithPolicies(Client client);

    @Mapping(target = "policyCount", expression = "java(getPolicyCount(client))")
    ClientSummaryDTO toSummaryDTO(Client client);

    @Mapping(target = "status", expression = "java(policy.getStatus().name())")
    PolicySummaryDTO toPolicySummaryDTO(Policy policy);

    List<PolicySummaryDTO> toPolicySummaryDTOList(List<Policy> policies);

    List<ClientSummaryDTO> toSummaryDTOList(List<Client> clients);

    List<ClientResponseDTO> toResponseDTOList(List<Client> clients);

    default Integer getPolicyCount(Client client) {
        if (client == null || client.getPolicies() == null) {
            return 0;
        }
        return client.getPolicies().size();
    }

    default String mapPolicyStatus(Policy.PolicyStatus status) {
        if (status == null) {
            return "UNKNOWN";
        }
        return status.name();
    }

    default String mapPolicyType(Policy.PolicyType type) {
        if (type == null) {
            return "UNKNOWN";
        }
        return type.getDisplayName();
    }
}
