package com.questify.backend.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // getter
    public String getToken() {
        return token;
    }
}
