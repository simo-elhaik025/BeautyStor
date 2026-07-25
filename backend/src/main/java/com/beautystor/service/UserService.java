package com.beautystor.service;

import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.dto.user.UpdateUserRequest;
import com.beautystor.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(CreateUserRequest request);
    List<UserResponse> getAll();
    UserResponse getById(long id);
    UserResponse update(long id, UpdateUserRequest request);
    void delete(long id);
}
