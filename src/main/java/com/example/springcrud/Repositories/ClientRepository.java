package com.example.springcrud.Repositories;

import com.example.springcrud.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Integer> {
    public Client findByEmail(String email);
}
