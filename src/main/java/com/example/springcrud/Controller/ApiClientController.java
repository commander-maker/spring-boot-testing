package com.example.springcrud.Controller;

import com.example.springcrud.Models.Client;
import com.example.springcrud.Repositories.ClientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/clients")
public class ApiClientController {
    private final ClientRepository repo;

    public ApiClientController(ClientRepository repo) {
        this.repo = repo;
    }

    // --- READ: all
    @GetMapping
    public List<Client> list() {
        return repo.findAll();
    }

    // --- READ: by id
    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable int id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Client body) {
        // auto-fill createdAt if missing
        if (body.getCreatedAt() == null) {
            body.setCreatedAt(Date.valueOf(LocalDate.now()));
        }
        body.setId(0); // ensure a new row

        try {
            Client saved = repo.save(body);
            return ResponseEntity
                    .created(URI.create("/api/clients/" + saved.getId()))
                    .body(saved);
        } catch (DataIntegrityViolationException e) {
            // likely unique email violation
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }
    }

    // --- UPDATE (full)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Client body) {
        return repo.findById(id).map(existing -> {
            existing.setName(body.getName());
            existing.setEmail(body.getEmail());
            existing.setAddress(body.getAddress());
            // keep original createdAt; if you want to allow change, uncomment:
            // existing.setCreatedAt(body.getCreatedAt());

            try {
                Client saved = repo.save(existing);
                return ResponseEntity.ok(saved);
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email already exists");
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return repo.findById(id).map(c -> {
            repo.delete(c);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
