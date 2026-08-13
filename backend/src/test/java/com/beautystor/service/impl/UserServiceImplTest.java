package com.beautystor.service.impl;

import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.entity.User;
import com.beautystor.enm.Role;
import com.beautystor.mapper.UserMapper;
import com.beautystor.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceImplTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RecordingUserRepository repositoryState = new RecordingUserRepository();
    private final UserRepository userRepository = repositoryState.asRepository();
    private final UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder, new UserMapper());

    @Test
    void createHashesPasswordBeforeSaving() {
        repositoryState.emailExists.set(false);

        var response = userService.create(new CreateUserRequest(
                "admin@beautystor.com",
                "Password123!",
                "ADMIN",
                "Admin",
                "User",
                "777777777",
                true));

        User savedUser = repositoryState.savedUser.get();
        assertEquals("admin@beautystor.com", savedUser.getEmail());
        assertEquals(Role.ADMIN, savedUser.getRole());
        assertTrue(passwordEncoder.matches("Password123!", savedUser.getPasswordHash()));
        assertEquals("admin@beautystor.com", response.getEmail());
        assertEquals("ADMIN", response.getRole());
    }

    @Test
    void createRejectsDuplicateEmail() {
        repositoryState.emailExists.set(true);

        assertThrows(IllegalArgumentException.class, () -> userService.create(new CreateUserRequest(
                "duplicate@beautystor.com",
                "Password123!",
                "USER",
                "First",
                "Last",
                "888888888",
                true)));
    }

    @Test
    void createRejectsUnknownRole() {
        repositoryState.emailExists.set(false);

        assertThrows(IllegalArgumentException.class, () -> userService.create(new CreateUserRequest(
                "role@beautystor.com",
                "Password123!",
                "SUPER_ADMIN",
                "First",
                "Last",
                "999999999",
                true)));
    }

    static class RecordingUserRepository implements InvocationHandler {
        final AtomicBoolean emailExists = new AtomicBoolean(false);
        final AtomicReference<User> savedUser = new AtomicReference<>();

        UserRepository asRepository() {
            return (UserRepository) Proxy.newProxyInstance(
                    UserRepository.class.getClassLoader(),
                    new Class<?>[]{UserRepository.class},
                    this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            String name = method.getName();
            if ("existsByEmail".equals(name)) {
                return emailExists.get();
            }
            if ("existsByEmailAndIdNot".equals(name)) {
                return false;
            }
            if ("existsById".equals(name)) {
                return savedUser.get() != null;
            }
            if ("findById".equals(name) || "findByEmail".equals(name)) {
                return Optional.ofNullable(savedUser.get());
            }
            if ("findAll".equals(name)) {
                return java.util.List.of();
            }
            if ("save".equals(name)) {
                User user = (User) args[0];
                if (savedUser.get() == null) {
                    user.setId(1L);
                } else {
                    user.setId(savedUser.get().getId());
                }
                savedUser.set(user);
                return user;
            }
            if ("deleteById".equals(name)
                    || "delete".equals(name)
                    || "deleteAll".equals(name)
                    || "deleteAllById".equals(name)
                    || "deleteAllInBatch".equals(name)
                    || "deleteAllByIdInBatch".equals(name)
                    || "deleteInBatch".equals(name)
                    || "flush".equals(name)
                    || "saveAndFlush".equals(name)
                    || "saveAll".equals(name)
                    || "saveAllAndFlush".equals(name)) {
                return null;
            }
            throw new UnsupportedOperationException("Method not supported in test: " + name);
        }
    }
}
