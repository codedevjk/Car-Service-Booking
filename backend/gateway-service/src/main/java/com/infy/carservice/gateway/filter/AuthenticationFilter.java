package com.infy.carservice.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (exchange.getRequest().getURI().getPath().contains("/api/auth")) {
                return chain.filter(exchange);
            }
            if (!exchange.getRequest().getHeaders().containsKey("X-User-Id")) {
                throw new RuntimeException("Missing X-User-Id header for string-based authentication");
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {}
}
