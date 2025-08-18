package com.karamatics.audit.audit_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "`sem_activity_log`")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestId;
    private String method;
    private String path;
    private String queryParams;
    private String requestHeaders;
    private String requestBody;
    private String clientIp;
    private String targetService;
    private String responseHeaders;
    private String responseBody;
    private String status;
    private Instant requestTimestamp;
    private Instant responseTimestamp;
    private int statusCode;
    private long processingTimeMs;
}
