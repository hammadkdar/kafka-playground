package com.karamatics.audit.audit_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/secure")
    public ResponseEntity<Map<String, Object>> secureEndpoint(Authentication authentication) {
        long requestStartTime = System.currentTimeMillis();
        String requestThread = Thread.currentThread().getName();

        return ResponseEntity.ok(Map.of(
            "message", "Access granted",
            "user", authentication.getName(),
            "authorities", authentication.getAuthorities().toString(),
            "requestThread", requestThread,
            "responseTime", System.currentTimeMillis() - requestStartTime + "ms"
        ));
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        return ResponseEntity.ok(Map.of(
            "service", "Audit Service",
            "status", "running",
            "thread", Thread.currentThread().getName()
        ));
    }
}
