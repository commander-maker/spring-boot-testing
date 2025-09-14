package com.example.springcrud;

import com.example.springcrud.Models.Client;
import com.example.springcrud.Repositories.ClientRepository; // Service layer for business logic
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;  // Mock the repository layer

    @InjectMocks
    private ClientService clientService;  // Service class where logic resides

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes mocks
        client = new Client();
        client.setId(10);
        client.setEmail("test@example.com");
        client.setName("John Doe");
        client.setAddress("123 Main St");
        client.setCreatedAt(Date.valueOf(LocalDate.now()));
    }

    // Test for successful client creation
    //@Test
    void testCreateClientSuccess() {
        // Arrange: mock the save operation to return the client
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act: call the service to create the client
        Client createdClient = clientService.createClient(client);



        // Assert: check that the created client is returned with a valid ID
        assertNotNull(createdClient);
        assertEquals("test@example.com", createdClient.getEmail());
        assertEquals("John Doe", createdClient.getName());
        assertEquals("123 Main St", createdClient.getAddress());
        assertTrue(createdClient.getId() > 0);  // ID should be auto-generated
    }

    // Test for duplicate email scenario (should throw exception)
    @Test
    void testCreateClientDuplicateEmail() {
        // Arrange: mock the repository to simulate an existing email
        when(clientRepository.findByEmail("test@example.com")).thenReturn(client);

        // Act & Assert: validate that the service throws an exception when the email exists
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clientService.createClient(client);
        });
        assertEquals("Email address is already used", exception.getMessage());
    }
}
