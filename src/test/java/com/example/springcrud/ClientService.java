package com.example.springcrud;

import com.example.springcrud.Models.Client;
import com.example.springcrud.Repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Create client and validate duplicate email
    public Client createClient(Client client) {
        // Validate if the email already exists
        if (clientRepository.findByEmail(client.getEmail()) != null) {
            throw new RuntimeException("Email address is already used");
        }

        // Save and return the client (with generated ID)
        return clientRepository.save(client);
    }
}
