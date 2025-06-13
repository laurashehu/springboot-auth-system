package com.auth.backend.DTO;


// DTO/AuthSignupRequest.java


import lombok.Data;

@Data
public class AuthSignupRequest {
    private String email;
    private String password;
    private String username;
}
