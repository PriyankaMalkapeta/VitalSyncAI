package com.vitalsync.api.web;

import com.vitalsync.api.service.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserDetailsService users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    public AuthController(UserDetailsService users, PasswordEncoder passwordEncoder, JwtService jwt) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        UserDetails user;
        try {
            user = users.loadUserByUsername(request.email());
        } catch (Exception ignored) {
            throw unauthorized();
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) throw unauthorized();
        JwtService.Token token = jwt.issue(user);
        return new LoginResponse(token.value(), "Bearer", token.expiresAt(), token.roles());
    }

    private ResponseStatusException unauthorized() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
    public record LoginResponse(String accessToken, String tokenType, Instant expiresAt, List<String> roles) {}
}
