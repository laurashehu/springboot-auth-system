package com.questify.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaskCreateRequest {
    private String title;
    private String description;
    private int xp;
    private LocalDateTime deadline;
    // getters and setters


}
