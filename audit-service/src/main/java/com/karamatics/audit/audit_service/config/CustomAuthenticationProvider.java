package com.karamatics.audit.audit_service.config;

import com.karamatics.audit.audit_service.event.AuthenticationFailureEvent;
import com.karamatics.audit.audit_service.event.AuthenticationSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String ipAddress = getClientIpAddress();

        try {
            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (!user.isAccountNonLocked()) {
                eventPublisher.publishEvent(new AuthenticationFailureEvent(this, username, ipAddress, "Account locked"));
                throw new LockedException("User account is locked");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                eventPublisher.publishEvent(new AuthenticationFailureEvent(this, username, ipAddress, "Bad credentials"));
                throw new BadCredentialsException("Invalid credentials");
            }

            // Successful authentication
            eventPublisher.publishEvent(new AuthenticationSuccessEvent(this, username, ipAddress));

            return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());

        } catch (UsernameNotFoundException e) {
            eventPublisher.publishEvent(new AuthenticationFailureEvent(this, username, ipAddress, "User not found"));
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String getClientIpAddress() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            String xForwardedFor = request.getHeader("X-Forwarded-For");
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                return xForwardedFor.split(",")[0].trim();
            }
            return request.getRemoteAddr();
        }
        return "unknown";
    }
}
