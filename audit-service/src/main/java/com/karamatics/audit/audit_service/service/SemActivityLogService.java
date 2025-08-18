package com.karamatics.audit.audit_service.service;

import com.karamatics.audit.audit_service.entity.SemActivityLog;
import com.karamatics.audit.audit_service.repository.SemActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemActivityLogService {

    private final SemActivityLogRepository semActivityLogRepository;

    public SemActivityLogService(SemActivityLogRepository semActivityLogRepository) {
        this.semActivityLogRepository = semActivityLogRepository;
    }

    public List<SemActivityLog> getAllAuditRecords() {
        return semActivityLogRepository.findAll();
    }

    public SemActivityLog save(SemActivityLog semActivityLog) {
        return semActivityLogRepository.save(semActivityLog);
    }
}
