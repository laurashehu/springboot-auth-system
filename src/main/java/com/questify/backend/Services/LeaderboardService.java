package com.questify.backend.Services;

import com.questify.backend.Entities.User;
import com.questify.backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final UserRepository userRepository;

    public List<User> getTopUsers(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("xp").descending());
        return userRepository.findAll(pageable).getContent();
    }
}
