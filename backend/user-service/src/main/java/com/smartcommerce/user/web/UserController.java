package com.smartcommerce.user.web;

import com.smartcommerce.user.service.UserService;
import com.smartcommerce.user.web.dto.CreateUserRequest;
import com.smartcommerce.user.web.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("service", "user-service", "status", "UP");
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest req) {
        var created = userService.create(req);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + created.getId()))
                .body(UserResponse.from(created));
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return UserResponse.from(userService.getById(id));
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.listAll().stream()
                .map(UserResponse::from)
                .toList();
    }
}