package com.example.spring_boot_starter_parent.Controller;


import com.example.spring_boot_starter_parent.DTO.Request.CreateClientDTO;
import com.example.spring_boot_starter_parent.DTO.Request.UpdateClientDTO;
import com.example.spring_boot_starter_parent.DTO.Response.ClientResponseDTO;
import com.example.spring_boot_starter_parent.DTO.Response.ClientSummaryDTO;
import com.example.spring_boot_starter_parent.Service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;


@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController (ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Creating new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody CreateClientDTO createClientDTO) {
        logger.info("Creating client with email: {}", createClientDTO.email());

        ClientResponseDTO newClient = clientService.createClient(createClientDTO);

        logger.info("Created client with ID: {}", newClient.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Return client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client got found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable Long clientId) {

        logger.info("Searching for client with ID: {}", clientId);

        ClientResponseDTO client = clientService.getActiveClientWithPolicies(clientId);

        return ResponseEntity.ok(client);
    }

    @PutMapping("/{clientId}")
    @Operation(summary = "Updating client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long clientId,
            @Valid @RequestBody UpdateClientDTO updateClientDTO
            ) {

        logger.info("Updating client with ID: {}", clientId);

        ClientResponseDTO updatedClient = clientService.updateClient(clientId, updateClientDTO);

        return ResponseEntity.ok(updatedClient);

    }

    @DeleteMapping("/{clientId}")
    @Operation(summary = "Deleting client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId)
    {
        logger.info("Deleting clint with ID: {}", clientId);

        clientService.deactivateClient(clientId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{clientId}/activate")
    @Operation(summary = "Client activation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client activated"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> activateClient(@PathVariable Long clientId)
    {
        logger.info("Activating client with ID: {}", clientId);

        clientService.activateClient(clientId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Searching for client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search finished"),
            @ApiResponse(responseCode = "400", description = "Empty search term")
    })
    public ResponseEntity<List<ClientSummaryDTO>> searchClients(@RequestParam("q") String searchTerm) {
        logger.info("Searching for client with search term: {}", searchTerm);

        List<ClientSummaryDTO> foundClients = clientService.searchActiveClients(searchTerm);

        return ResponseEntity.ok(foundClients);
    }

}
