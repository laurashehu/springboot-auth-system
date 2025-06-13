package com.questify.backend.Controllers;

import com.questify.backend.DTO.TaskCreateRequest;
import com.questify.backend.Entities.Task;
import com.questify.backend.Entities.User;
import com.questify.backend.Services.TaskService;
import com.questify.backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @PostMapping
    public Task createTask(
            Authentication authentication,
            @RequestBody TaskCreateRequest request
    ) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        return taskService.createTask(
                user.getId(),
                request.getTitle(),
                request.getDescription(),
                request.getXp(),
                request.getDeadline()
        );
    }


    @GetMapping("/user/{username}")
    public List<Task> getUserTasks(@PathVariable String username) {
        User user = userService.findByUsername(username).orElseThrow();
        return taskService.listTasksByUser(user.getId());
    }


    @GetMapping("/today")
    public List<Task> getTodayTasks(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        return taskService.getTodayTasks(user.getId()); // implement this method to return today's tasks
    }

    @PutMapping("/{taskId}/complete")
    public Task completeTask(
            @PathVariable Long taskId,
            Authentication authentication
    ) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        return taskService.completeTask(taskId, user.getId());
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(
            @PathVariable Long taskId,
            Authentication authentication
    ) {
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        taskService.deleteTask(taskId, user.getId());
    }
}
