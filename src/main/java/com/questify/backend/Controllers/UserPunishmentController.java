package com.questify.backend.Controllers;

import com.questify.backend.DTO.UserPunishmentDTO;
import com.questify.backend.Entities.User;
import com.questify.backend.Services.UserPunishmentService;
import com.questify.backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-punishments")
@RequiredArgsConstructor

public class UserPunishmentController {

    private final UserPunishmentService userPunishmentService;
    private final UserService userService;

    @PostMapping("/assign")
    public UserPunishmentDTO assignPunishment(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return new UserPunishmentDTO(userPunishmentService.assignRandomPunishment(user));
    }

    @GetMapping
    public List<UserPunishmentDTO> getPunishments(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        return userPunishmentService.getPunishmentsForUser(user).stream()
                .map(UserPunishmentDTO::new)
                .collect(Collectors.toList());
    }

}
