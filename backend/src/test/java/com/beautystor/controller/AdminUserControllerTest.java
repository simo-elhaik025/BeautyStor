package com.beautystor.controller;

import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.dto.user.UserResponse;
import com.beautystor.security.JwtAuthenticationFilter;
import com.beautystor.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = AdminUserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@Import(AdminUserControllerTest.TestBeans.class)
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUserService testUserService;

    @BeforeEach
    void resetState() {
        testUserService.reset();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanListUsers() throws Exception {
        testUserService.listResult = new PageImpl<>(List.of(
                new UserResponse(1L, "admin@beautystor.com", "ADMIN", "Admin", "User", "111111111", true)));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].email").value("admin@beautystor.com"))
                .andExpect(jsonPath("$.data.content[0].password").doesNotExist())
                .andExpect(jsonPath("$.data.content[0].passwordHash").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanConsultUser() throws Exception {
        testUserService.getByIdResult = new UserResponse(
                1L, "user@beautystor.com", "USER", "Ahmed", "Delivery", "222222222", true);

        mockMvc.perform(get("/api/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("user@beautystor.com"))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.passwordHash").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminGets404WhenUserDoesNotExist() throws Exception {
        testUserService.getByIdFailure = new EntityNotFoundException("User with ID 99 not found");

        mockMvc.perform(get("/api/admin/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message").value("User with ID 99 not found"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateUser() throws Exception {
        testUserService.createResult = new UserResponse(
                10L, "user@beautystor.com", "USER", "Ahmed", "Delivery", "333333333", true);

        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Ahmed",
                                  "lastName": "Delivery",
                                  "email": "user@beautystor.com",
                                  "password": "Password123!",
                                  "role": "USER",
                                  "phone": "333333333"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.passwordHash").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateDeliveryAgent() throws Exception {
        testUserService.createResult = new UserResponse(
                11L, "delivery@beautystor.com", "DELIVERY_AGENT", "Ahmed", "Delivery", "444444444", true);

        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Ahmed",
                                  "lastName": "Delivery",
                                  "email": "delivery@beautystor.com",
                                  "password": "Password123!",
                                  "role": "DELIVERY_AGENT",
                                  "phone": "444444444"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.role").value("DELIVERY_AGENT"))
                .andExpect(jsonPath("$.data.password").doesNotExist())
                .andExpect(jsonPath("$.data.passwordHash").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void invalidRoleReturns400() throws Exception {
        testUserService.createFailure = new IllegalArgumentException("Invalid role: SUPER_ADMIN");

        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Ahmed",
                                  "lastName": "Delivery",
                                  "email": "invalid-role@beautystor.com",
                                  "password": "Password123!",
                                  "role": "SUPER_ADMIN",
                                  "phone": "555555555"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Invalid role: SUPER_ADMIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanActivateOrDeactivateUser() throws Exception {
        testUserService.setActiveResult = new UserResponse(
                7L, "user@beautystor.com", "USER", "Ahmed", "Delivery", "666666666", false);

        mockMvc.perform(patch("/api/admin/users/7/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "active": false }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.active").value(false));
    }

    @Test
    @WithMockUser(roles = "USER")
    void userGets403() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "DELIVERY_AGENT")
    void deliveryAgentGets403() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void nonAuthenticatedGets401() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isUnauthorized());
    }

    @TestConfiguration
    static class TestBeans {

        @Bean
        TestUserService testUserService() {
            return new TestUserService();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling(exceptions -> exceptions
                            .authenticationEntryPoint(unauthorizedEntryPoint())
                            .accessDeniedHandler(forbiddenHandler()))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/admin/users/**").hasRole("ADMIN")
                            .anyRequest().permitAll())
                    .build();
        }

        @Bean
        AuthenticationEntryPoint unauthorizedEntryPoint() {
            return (request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

        @Bean
        AccessDeniedHandler forbiddenHandler() {
            return new AccessDeniedHandler() {
                @Override
                public void handle(
                        jakarta.servlet.http.HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException {
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            };
        }
    }

    static class TestUserService implements UserService {
        private Page<UserResponse> listResult = Page.empty();
        private UserResponse getByIdResult;
        private RuntimeException getByIdFailure;
        private UserResponse createResult;
        private UserResponse setActiveResult;
        private RuntimeException setActiveFailure;
        private RuntimeException createFailure;

        void reset() {
            listResult = Page.empty();
            getByIdResult = null;
            getByIdFailure = null;
            createResult = null;
            setActiveResult = null;
            setActiveFailure = null;
            createFailure = null;
        }

        @Override
        public UserResponse create(CreateUserRequest request) {
            if (createFailure != null) {
                throw createFailure;
            }
            return createResult;
        }

        @Override
        public List<UserResponse> getAll() {
            return listResult.getContent();
        }

        @Override
        public Page<UserResponse> getAll(Pageable pageable) {
            return listResult;
        }

        @Override
        public UserResponse getById(long id) {
            if (getByIdFailure != null) {
                throw getByIdFailure;
            }
            return getByIdResult;
        }

        @Override
        public UserResponse update(long id, com.beautystor.dto.user.UpdateUserRequest request) {
            throw new UnsupportedOperationException("Not needed for this test");
        }

        @Override
        public UserResponse setActive(long id, boolean active) {
            if (setActiveFailure != null) {
                throw setActiveFailure;
            }
            return setActiveResult;
        }

        @Override
        public void delete(long id) {
            throw new UnsupportedOperationException("Not needed for this test");
        }
    }
}
