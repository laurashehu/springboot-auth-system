package com.auth.backend.Services;

import com.auth.backend.Entities.User;
import com.auth.backend.Security.JWTokenProvider;
import com.auth.backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt
    private final JWTokenProvider jwtTokenProvider;

    private static final int DAILY_XP_CAP = 5000;

    public User createUser(String email, String username, String password) {
        if (existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);    // <-- Make sure this is username, not password!
        user.setPassword(passwordEncoder.encode(password));  // encode password


        return userRepository.save(user);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }




}
