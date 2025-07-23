package com.example.spring_boot_starter_parent.Service;


import com.example.spring_boot_starter_parent.DTO.Request.CreateClientDTO;
import com.example.spring_boot_starter_parent.DTO.Request.UpdateClientDTO;
import com.example.spring_boot_starter_parent.DTO.Response.ClientResponseDTO;
import com.example.spring_boot_starter_parent.DTO.Response.ClientSummaryDTO;
import com.example.spring_boot_starter_parent.Exception.ClientNotFoundException;
import com.example.spring_boot_starter_parent.Exception.DuplicateEmailException;
import com.example.spring_boot_starter_parent.Mapper.ClientMapper;
import com.example.spring_boot_starter_parent.Model.Client;
import com.example.spring_boot_starter_parent.Repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public ClientResponseDTO createClient(CreateClientDTO createClientDTO) {
        logger.debug("Creating new client with email: {}", createClientDTO.email());

        //Provera da li email vec postoji
        if(clientRepository.existsByEmail(createClientDTO.email())) {
            logger.warn("Attempt to create client with existing email: {}", createClientDTO.email());
            throw new DuplicateEmailException("Client with email" + createClientDTO.email() + "already exists");
        }

        Client client = clientMapper.toEntity(createClientDTO);

        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());

        //Funkcija za cuvanje u klijenta u bazi
        Client savedClient = clientRepository.save(client);

        logger.info("Successfully created client with ID: {} and email: {}", savedClient.getId(),
                savedClient.getEmail());

        return clientMapper.toResponseDTO(savedClient);
    }

    public ClientResponseDTO getActiveClientWithPolicies(Long clientId) {
        logger.debug("Fetching active client with policies, ID: {}", clientId);

        Client client = clientRepository.findActiveClientWithPolicies(clientId)
                .orElseThrow(() -> {
                    logger.warn("Active client not found with ID: {}", clientId);
                    return new ClientNotFoundException("Active client not found with ID: " + clientId);
                });

        return clientMapper.toResponseDTOWithPolicies(client);
    }

    @Transactional
    public ClientResponseDTO updateClient(Long clientId, UpdateClientDTO updateClientDTO) {
        logger.debug("Updating client with ID: {}", clientId);

        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    logger.warn("Client not found for update with ID: {}", clientId);
                    return new ClientNotFoundException("Client not found with ID: {}" + clientId);
    });


        //Validacija email-a
        if (updateClientDTO.email() != null && 
        !updateClientDTO.email().equals(existingClient.getEmail()) &&
                clientRepository.existsByEmail(updateClientDTO.email())) {
            logger.warn("Attempt to update client {} with exisitng email: {}", clientId, updateClientDTO.email());
            throw new DuplicateEmailException("Client with email " + updateClientDTO.email() + " already exists");
        }

        clientMapper.updateEntityFromDTO(updateClientDTO, existingClient);
        existingClient.setUpdatedAt(LocalDateTime.now());

        Client updatedClient = clientRepository.save(existingClient);

        logger.info("Successfully updated client with ID: {}", clientId);

        return clientMapper.toResponseDTO(updatedClient);
    }

    //Soft delete funkcija
    @Transactional
    public void deactivateClient(Long clientId)
    {
        logger.debug("Deactivating client with ID : {}", clientId);
        
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    logger.warn("Client not found for deactivation with ID : {}", clientId);
                    return new ClientNotFoundException("Client not found with ID: {}"+ clientId);
                });
        
        client.setActive(false);
        client.setUpdatedAt(LocalDateTime.now());
        clientRepository.save(client);
        
        logger.info("Successfully deactivated client with ID: {}", clientId);
    }
    
    
    @Transactional
    public void activateClient(Long clientId){
        logger.debug("Activating client with ID: {}", clientId);
        
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    logger.warn("Client not found for activation with ID: {}", clientId);
                    return new ClientNotFoundException("Client not found with ID : {}"+ clientId);
                });
        
        client.setActive(true);
        client.setUpdatedAt(LocalDateTime.now());
        clientRepository.save(client);
        
        logger.info("Successfully activated client with ID: {}", clientId);
        
    }
    
    public List<ClientSummaryDTO> searchActiveClients(String searchTerm) {
        logger.debug("Searching for active clients with term: {}", searchTerm);
        
        if(searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }

        List<Client> clients = clientRepository.findActiveClientsBySearchTerm(searchTerm.trim());
        return clients.stream()
                .map(clientMapper::toSummaryDTO)
                .toList();
    }
    
    public boolean existsById(Long clientId){
        return clientRepository.existsById(clientId);
    }

    public boolean existsByEmail(String email){
        return clientRepository.existsByEmail(email);
    }



}
