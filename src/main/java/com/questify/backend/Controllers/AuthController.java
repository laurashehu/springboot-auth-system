package com.questify.backend.Controllers;

import com.questify.backend.DTO.AuthLoginRequest;
import com.questify.backend.DTO.AuthSignupRequest;

import com.questify.backend.DTO.AuthResponse;
import com.questify.backend.Services.UserService;
import com.questify.backend.Security.JWTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email must not be empty");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password must not be empty");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = tokenProvider.createToken(request.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid email/password");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthSignupRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already in use");
        }
        if (request.getPassword().length() < 8 || !request.getPassword().matches(".*[!@#$%^&*()].*")) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters and contain a special character");
        }



        userService.createUser(request.getEmail(), request.getUsername(), request.getPassword());


        return ResponseEntity.ok("User registered successfully");
    }
}
