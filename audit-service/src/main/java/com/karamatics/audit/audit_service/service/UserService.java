package com.karamatics.audit.audit_service.service;

import com.karamatics.audit.audit_service.entity.User;
import com.karamatics.audit.audit_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#username", condition = "#username != null")
    public Optional<User> findByUsername(String username) {
        log.debug("Loading user from database: {}", username);
        return userRepository.findByUsername(username);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#username", condition = "#username != null")
    public void updateFailedAttempts(String username, int attempts) {
        log.debug("Updating failed attempts for user: {} to {}", username, attempts);
        userRepository.updateFailedAttempts(username, attempts);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#username", condition = "#username != null")
    public void lockUser(String username) {
        log.debug("Locking user: {}", username);
        userRepository.lockUser(username, false, LocalDateTime.now());
    }

    @Transactional
    @CacheEvict(value = "users", key = "#username", condition = "#username != null")
    public void unlockUser(String username) {
        log.debug("Unlocking user: {}", username);
        userRepository.lockUser(username, true, null);
        userRepository.updateFailedAttempts(username, 0);
    }
}
