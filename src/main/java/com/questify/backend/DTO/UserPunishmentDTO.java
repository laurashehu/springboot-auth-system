package com.questify.backend.DTO;

import com.questify.backend.Entities.UserPunishment;

import java.time.format.DateTimeFormatter;

public class UserPunishmentDTO {
    private Long id;
    private String description;
    private String assignedAt;
    private boolean acknowledged;

    public UserPunishmentDTO(UserPunishment up) {
        this.id = up.getId();
        this.description = up.getPunishment().getDescription();
        // Format assignedAt to ISO string for frontend
        this.assignedAt = up.getAssignedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.acknowledged = up.isAcknowledged();
    }

    // Getters and setters (or use Lombok @Getter/@Setter)
    public Long getId() { return id; }
    public String getDescription() { return description; }
    public String getAssignedAt() { return assignedAt; }
    public boolean isAcknowledged() { return acknowledged; }
}
