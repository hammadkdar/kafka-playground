package com.karamatics.audit.audit_service.repository;

import com.karamatics.audit.audit_service.entity.SemActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemActivityLogRepository extends JpaRepository<SemActivityLog, Long> {
}
