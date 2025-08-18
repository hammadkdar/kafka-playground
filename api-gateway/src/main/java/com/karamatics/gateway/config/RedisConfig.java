package com.karamatics.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RedisConfig {

    @Bean
    public KeyResolver customKeyResolver() {
        // This is a placeholder for a custom key resolver.
        // You can implement your own logic to resolve keys based on the request.
        return exchange -> {
            // Example: Use the request path as the key
            String path = exchange.getRequest().getPath().value();
            return Mono.just(path);
        };
    }
}
