package com.karamatics.audit.audit_service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AuthenticationFailureEvent extends ApplicationEvent {

    private final String username;
    private final String ipAddress;
    private final String reason;
    private final LocalDateTime eventTimestamp;

    public AuthenticationFailureEvent(Object source, String username, String ipAddress, String reason) {
        super(source);
        this.username = username;
        this.ipAddress = ipAddress;
        this.reason = reason;
        this.eventTimestamp = LocalDateTime.now();
    }
}
