package com.shoppingcart.security.constants;

/**
 * Security constants for authentication and authorization.
 */
public final class SecurityConstants {

    private SecurityConstants() {
    }

    /**
     * URLs that are publicly accessible without authentication.
     */
    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/api/v1/public/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/health"
    };

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
}