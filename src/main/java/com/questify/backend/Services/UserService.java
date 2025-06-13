package com.questify.backend.Services;

import com.questify.backend.Entities.User;
import com.questify.backend.Security.JWTokenProvider;
import com.questify.backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        user.setXp(0);
        user.setLevel(1);
        user.setDailyXp(0);
        user.setXpDate(LocalDate.now());

        return userRepository.save(user);
    }


    public User addXp(User user, int xpToAdd) {
        LocalDate today = LocalDate.now();
        if(user.getXpDate() == null || !user.getXpDate().equals(today)) {
            user.setDailyXp(0);
            user.setXpDate(today);
        }

        int allowedXp = Math.min(xpToAdd, DAILY_XP_CAP - user.getDailyXp());
        if(allowedXp <= 0) {
            throw new RuntimeException("Daily XP cap reached");
        }

        user.setXp(user.getXp() + allowedXp);
        user.setDailyXp(user.getDailyXp() + allowedXp);
        updateLevel(user);

        return userRepository.save(user);
    }

    private void updateLevel(User user) {
        // Simple example: level up every 1000 XP
        int newLevel = user.getXp() / 1000 + 1;
        if(newLevel > user.getLevel()) {
            user.setLevel(newLevel);
        }
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
