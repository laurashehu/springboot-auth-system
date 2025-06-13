package com.questify.backend.Controllers;

import com.questify.backend.Entities.User;
import com.questify.backend.Services.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    // Get top N users by XP
    @GetMapping("/top")
    public List<User> getTopUsers(@RequestParam(defaultValue = "10") int limit) {
        return leaderboardService.getTopUsers(limit);
    }
}