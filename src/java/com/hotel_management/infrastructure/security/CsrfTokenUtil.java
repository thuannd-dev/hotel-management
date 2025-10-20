package com.hotel_management.infrastructure.security;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for generating secure CSRF tokens
 * @author thuannd.dev
 */
public final class CsrfTokenUtil {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    private CsrfTokenUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generate a cryptographically secure random CSRF token
     * @return Base64-encoded random token (32 bytes)
     */
    public static String generateToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

