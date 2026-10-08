package com.smartcommerce.user.web.dto;

import com.smartcommerce.user.domain.User;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String role,
        boolean enabled,
        Instant createdAt
) {
    public static UserResponse from(User u) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getRole().name(),
                u.isEnabled(),
                u.getCreatedAt()
        );
    }
}