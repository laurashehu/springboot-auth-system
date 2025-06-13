package com.questify.backend.Entities;

import com.questify.backend.Entities.Punishment;
import com.questify.backend.Repositories.PunishmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PunishmentDataLoader {

    private final PunishmentRepository punishmentRepository;

    @PostConstruct
    public void seedPunishments() {
        if (punishmentRepository.count() == 0) {
            List<Punishment> punishments = List.of(
                    new Punishment(null, "Do 20 push-ups"),
                    new Punishment(null, "Sing a song aloud"),
                    new Punishment(null, "Write a 100-word journal entry"),
                    new Punishment(null, "No social media for 1 hour"),
                    new Punishment(null, "Drink a glass of water"),
                    new Punishment(null, "Take a 10-minute walk"),
                    new Punishment(null, "Clean your workspace"),
                    new Punishment(null, "Read a page from a book"),
                    new Punishment(null, "Do 10 jumping jacks"),
                    new Punishment(null, "Organize your desktop files")
            );
            punishmentRepository.saveAll(punishments);
        }
    }
}
