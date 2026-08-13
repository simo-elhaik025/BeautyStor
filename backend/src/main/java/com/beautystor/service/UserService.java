package com.beautystor.service;

import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.dto.user.UpdateUserRequest;
import com.beautystor.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse create(CreateUserRequest request);
    List<UserResponse> getAll();
    Page<UserResponse> getAll(Pageable pageable);
    UserResponse getById(long id);
    UserResponse update(long id, UpdateUserRequest request);
    UserResponse setActive(long id, boolean active);
    void delete(long id);
}
