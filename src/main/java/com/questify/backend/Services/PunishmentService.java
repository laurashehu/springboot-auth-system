package com.questify.backend.Services;

import com.questify.backend.Entities.Punishment;
import com.questify.backend.Entities.UserPunishment;
import com.questify.backend.Repositories.PunishmentRepository;
import com.questify.backend.Repositories.UserPunishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PunishmentService {
    private final PunishmentRepository punishmentRepository;
    private final UserPunishmentRepository userPunishmentRepository;
    public Punishment createPunishment(Punishment punishment) {
        return punishmentRepository.save(punishment);
    }

    public List<Punishment> getAllPunishments() {
        return punishmentRepository.findAll();
    }
    public Optional<UserPunishment> getLatestUnacknowledgedPunishment(Long userId) {
        return userPunishmentRepository.findLatestUnacknowledgedByUserId(userId);
    }

}
