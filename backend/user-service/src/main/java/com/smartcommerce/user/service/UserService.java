package com.smartcommerce.user.service;

import com.smartcommerce.user.domain.User;
import com.smartcommerce.user.exception.EmailAlreadyExistsException;
import com.smartcommerce.user.exception.UserNotFoundException;
import com.smartcommerce.user.repository.UserRepository;
import com.smartcommerce.user.web.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(CreateUserRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new EmailAlreadyExistsException(req.email());
        }
        User u = new User();
        u.setEmail(req.email());
        // TODO: hash with BCrypt once Spring Security is wired in
        u.setPasswordHash("{noop}" + req.password());
        u.setFirstName(req.firstName());
        u.setLastName(req.lastName());
        return userRepository.save(u);
    }

    @Transactional(readOnly = true)
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return userRepository.findAll();
    }
}