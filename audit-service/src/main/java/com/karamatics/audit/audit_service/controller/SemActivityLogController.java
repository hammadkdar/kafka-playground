package com.karamatics.audit.audit_service.controller;

import com.karamatics.audit.audit_service.entity.SemActivityLog;
import com.karamatics.audit.audit_service.service.SemActivityLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit-records")
@Slf4j
public class SemActivityLogController {

    private final SemActivityLogService semActivityLogService;

    public SemActivityLogController(SemActivityLogService semActivityLogService) {
        this.semActivityLogService = semActivityLogService;
    }

    @GetMapping
    public List<SemActivityLog> getAuditRecords() {
        log.info("Fetching all audit records");
        return semActivityLogService.getAllAuditRecords();
    }
}
