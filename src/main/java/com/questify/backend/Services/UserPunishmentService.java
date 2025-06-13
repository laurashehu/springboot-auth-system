package com.questify.backend.Services;

import com.questify.backend.Entities.Punishment;
import com.questify.backend.Entities.User;
import com.questify.backend.Entities.UserPunishment;
import com.questify.backend.Repositories.PunishmentRepository;
import com.questify.backend.Repositories.UserPunishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPunishmentService {

    private final UserPunishmentRepository userPunishmentRepository;
    private final PunishmentRepository punishmentRepository;

    public UserPunishment assignRandomPunishment(User user) {
        Punishment punishment = punishmentRepository.findRandomPunishment();
        UserPunishment up = new UserPunishment();
        up.setUser(user);
        up.setPunishment(punishment);
        up.setAssignedAt(LocalDateTime.now());
        up.setAcknowledged(false);
        return userPunishmentRepository.save(up);
    }

    public List<UserPunishment> getPunishmentsForUser(User user) {
        return userPunishmentRepository.findByUser(user);
    }
}
