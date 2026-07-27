package com.patient.management.authservice.controller;

import com.patient.management.authservice.dto.LoginRequestDTO;
import com.patient.management.authservice.dto.LoginResponseDTO;
import com.patient.management.authservice.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate a token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> optionalToken = authService.authenticateUser(loginRequestDTO);

        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Validate the generated token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
