package com.questify.backend.Services;

import com.questify.backend.Entities.Task;
import com.questify.backend.Entities.User;
import com.questify.backend.Repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserPunishmentService userPunishmentService;

    private static final int MAX_TASK_XP = 1000;

    public Task createTask(Long userId, String title, String description, int xp, LocalDateTime deadline) {
        if (xp <= 0 || xp > MAX_TASK_XP) {
            throw new IllegalArgumentException("XP must be between 1 and " + MAX_TASK_XP);
        }

        User user = userService.findById(userId);

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setXp(xp);
        task.setDeadline(deadline);
        task.setCompleted(false);
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public Task completeTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.isCompleted()) {
            throw new RuntimeException("Task already completed");
        }

        if (!task.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        if (task.isFailed()) {
            throw new RuntimeException("Cannot complete a failed task");
        }


        task.setCompleted(true);
        taskRepository.save(task);

        userService.addXp(task.getCreatedBy(), task.getXp());

        return task;
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        taskRepository.delete(task);
    }

    public List<Task> listTasksByUser(Long userId) {
        User user = userService.findById(userId);
        return taskRepository.findByCreatedBy(user);
    }

    public List<Task> getTodayTasks(Long userId) {
        User user = userService.findById(userId);
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return taskRepository.findByCreatedByAndCreatedAtAfter(user, startOfDay);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Transactional
    @Scheduled(fixedRate = 60000) // every minute
    public void checkExpiredTasks() {
        List<Task> expired = taskRepository.findByCompletedFalseAndFailedFalseAndDeadlineBefore(LocalDateTime.now());
        for (Task task : expired) {
            task.setFailed(true); // mark as failed
            taskRepository.save(task); // update task

            // assign punishment
            userPunishmentService.assignRandomPunishment(task.getCreatedBy());
        }
    }

}
