package com.karamatics.audit.audit_service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AuthenticationSuccessEvent extends ApplicationEvent {

    private final String username;
    private final String ipAddress;
    private final LocalDateTime eventTimestamp;

    public AuthenticationSuccessEvent(Object source, String username, String ipAddress) {
        super(source);
        this.username = username;
        this.ipAddress = ipAddress;
        this.eventTimestamp = LocalDateTime.now();
    }
}
