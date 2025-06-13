package com.questify.backend.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPunishment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Punishment punishment;

    private LocalDateTime assignedAt;

    private boolean acknowledged; // Optional: to track if the user has seen it
}
