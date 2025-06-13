package com.questify.backend.Controllers;

import com.questify.backend.Entities.Punishment;
import com.questify.backend.Entities.User;
import com.questify.backend.Services.PunishmentService;
import com.questify.backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/punishments")
@RequiredArgsConstructor
public class PunishmentController {
    private final PunishmentService punishmentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Punishment> createPunishment(@RequestBody Punishment punishment) {
        Punishment created = punishmentService.createPunishment(punishment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Punishment> getAllPunishments() {
        return punishmentService.getAllPunishments();
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestPunishmentForUser(Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return punishmentService.getLatestUnacknowledgedPunishment(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


}
