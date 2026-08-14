package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.dto.user.UpdateUserRequest;
import com.beautystor.dto.user.UserResponse;
import com.beautystor.service.UserService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Gestion administrative des utilisateurs.")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody CreateUserRequest request) {
        UserResponse responseDto = userService.create(request);
        ApiResponse<UserResponse> response = new ApiResponse<>(responseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {
        List<UserResponse> users = userService.getAll();
        ApiResponse<List<UserResponse>> response = new ApiResponse<>(users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable long id) {
        UserResponse user = userService.getById(id);
        ApiResponse<UserResponse> response = new ApiResponse<>(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable long id, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse updated = userService.update(id, request);
        ApiResponse<UserResponse> response = new ApiResponse<>(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
