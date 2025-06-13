package com.questify.backend.Repositories;

import com.questify.backend.Entities.UserPunishment;
import com.questify.backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPunishmentRepository extends JpaRepository<UserPunishment, Long> {
    List<UserPunishment> findByUser(User user);

    @Query("SELECT up FROM UserPunishment up WHERE up.user.id = :userId AND up.acknowledged = false ORDER BY up.assignedAt DESC")
    Optional<UserPunishment> findLatestUnacknowledgedByUserId(@Param("userId") Long userId);

}
