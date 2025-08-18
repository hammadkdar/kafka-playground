package com.karamatics.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/sea-register-service")
    public String seaRegisterServiceFallback() {
        return "SEA Register Service is currently unavailable. Please try again later.";
    }

    @GetMapping("/sem-audit-service")
    public String semAuditServiceFallback() {
        return "SEM Audit Service is currently unavailable. Please try again later.";
    }
}
