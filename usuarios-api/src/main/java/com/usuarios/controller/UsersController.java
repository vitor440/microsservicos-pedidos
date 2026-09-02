package com.usuarios.controller;

import com.usuarios.dto.UserRequest;
import com.usuarios.service.KeyCloackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final KeyCloackService service;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> teste() {

        return ResponseEntity.ok("Teste");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testeAdmin() {

        return ResponseEntity.ok("Teste-admin");
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> createUser(@RequestBody UserRequest request) {

        service.createUser(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createAdmin(@RequestBody UserRequest request) {

        service.createAdmin(request);
        return ResponseEntity.noContent().build();
    }
}
