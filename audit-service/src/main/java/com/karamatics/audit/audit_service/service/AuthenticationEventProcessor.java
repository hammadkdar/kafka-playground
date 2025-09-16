package com.karamatics.audit.audit_service.service;

import com.karamatics.audit.audit_service.entity.User;
import com.karamatics.audit.audit_service.event.AuthenticationFailureEvent;
import com.karamatics.audit.audit_service.event.AuthenticationSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEventProcessor {

    private final UserService userService;
    private static final int MAX_FAILED_ATTEMPTS = 3;

    @EventListener
    @Async("authenticationEventExecutor")
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        log.info("Authentication successful for user: {} from IP: {} [Thread: {}]",
                event.getUsername(), event.getIpAddress(), Thread.currentThread().getName());

        // Reset failed attempts on successful authentication
        userService.findByUsername(event.getUsername()).ifPresent(user -> {
            if (user.getFailedAttempts() > 0) {
                userService.updateFailedAttempts(event.getUsername(), 0);
            }
        });
    }

    @EventListener
    @Async("authenticationEventExecutor")
    public void handleAuthenticationFailure(AuthenticationFailureEvent event) {
        log.warn("Authentication failed for user: {} from IP: {}, reason: {} [Thread: {}]",
                event.getUsername(), event.getIpAddress(), event.getReason(), Thread.currentThread().getName());

        userService.findByUsername(event.getUsername()).ifPresent(user -> {
            int attempts = user.getFailedAttempts() + 1;

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                userService.lockUser(event.getUsername());
                log.warn("User {} has been locked after {} failed attempts",
                        event.getUsername(), attempts);
            } else {
                userService.updateFailedAttempts(event.getUsername(), attempts);
                log.info("Updated failed attempts for user {} to {}",
                        event.getUsername(), attempts);
            }
        });
    }
}
