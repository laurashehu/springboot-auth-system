package com.auth.backend.DTO;

// DTO/AuthLoginRequest.java


import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}
