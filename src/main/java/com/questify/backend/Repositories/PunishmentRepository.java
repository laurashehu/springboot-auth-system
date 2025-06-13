package com.questify.backend.Repositories;

import com.questify.backend.Entities.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PunishmentRepository extends JpaRepository<Punishment, Long> {
    @Query(value = "SELECT * FROM punishment ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Punishment findRandomPunishment();
}
