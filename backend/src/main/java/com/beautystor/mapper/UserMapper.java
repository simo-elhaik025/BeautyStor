package com.beautystor.mapper;

import com.beautystor.dto.user.UserResponse;
import com.beautystor.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : null,
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.isActive());
    }

    public Page<UserResponse> toResponse(Page<User> users) {
        return users.map(this::toResponse);
    }
}
