package com.beautystor.service.impl;

import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.dto.user.UpdateUserRequest;
import com.beautystor.dto.user.UserResponse;
import com.beautystor.entity.User;
import com.beautystor.enm.Role;
import com.beautystor.mapper.UserMapper;
import com.beautystor.repository.UserRepository;
import com.beautystor.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email " + request.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(parseRole(request.getRole()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setActive(request.getActive() == null || request.getActive());

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        return userMapper.toResponse(userRepository.findAll(pageable));
    }

    @Override
    public UserResponse getById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse update(long id, UpdateUserRequest request) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new IllegalArgumentException("Email " + request.getEmail() + " already in use");
        }

        User user = userRepository.findById(id).orElseThrow();

        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        user.setRole(parseRole(request.getRole()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setActive(request.getActive() != null ? request.getActive() : user.isActive());

        User updated = userRepository.save(user);
        return userMapper.toResponse(updated);
    }

    @Override
    public UserResponse setActive(long id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));

        user.setActive(active);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    private Role parseRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role is required");
        }

        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
