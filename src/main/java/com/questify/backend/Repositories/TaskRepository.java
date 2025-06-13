package com.questify.backend.Repositories;

import com.questify.backend.Entities.Task;
import com.questify.backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedBy(User user);
    List<Task> findByCreatedByAndCreatedAtAfter(User user, LocalDateTime time);
    List<Task> findByCompletedFalseAndFailedFalseAndDeadlineBefore(LocalDateTime now);

}
