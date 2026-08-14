package com.beautystor.controller;

import com.beautystor.dto.dashboard.AdminDashboardResponse;
import com.beautystor.security.JwtAuthenticationFilter;
import com.beautystor.service.AdminDashboardService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = AdminDashboardController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@Import(AdminDashboardControllerTest.TestBeans.class)
class AdminDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestAdminDashboardService testAdminDashboardService;

    @BeforeEach
    void resetState() {
        testAdminDashboardService.reset();
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "ADMIN")
    void adminCanAccessDashboard() throws Exception {
        testAdminDashboardService.result = new AdminDashboardResponse(
                12L,
                20L,
                5L,
                11L,
                4L,
                8L,
                15L,
                3L,
                42L,
                new BigDecimal("1234.50"));

        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalUsers").value(12))
                .andExpect(jsonPath("$.data.totalOrders").value(20))
                .andExpect(jsonPath("$.data.pendingOrders").value(5))
                .andExpect(jsonPath("$.data.deliveredOrders").value(11))
                .andExpect(jsonPath("$.data.cancelledOrders").value(4))
                .andExpect(jsonPath("$.data.totalProducts").value(8))
                .andExpect(jsonPath("$.data.totalProductVariants").value(15))
                .andExpect(jsonPath("$.data.outOfStockVariants").value(3))
                .andExpect(jsonPath("$.data.totalStockQuantity").value(42))
                .andExpect(jsonPath("$.data.deliveredRevenue").value(1234.50));
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "USER")
    void userGets403() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "DELIVERY_AGENT")
    void deliveryAgentGets403() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isForbidden());
    }

    @Test
    void nonAuthenticatedGets401() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isUnauthorized());
    }

    @TestConfiguration
    static class TestBeans {

        @Bean
        TestAdminDashboardService testAdminDashboardService() {
            return new TestAdminDashboardService();
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
                            .requestMatchers("/api/admin/dashboard").hasRole("ADMIN")
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

    static class TestAdminDashboardService implements AdminDashboardService {
        private AdminDashboardResponse result;

        void reset() {
            result = null;
        }

        @Override
        public AdminDashboardResponse getDashboard() {
            return result;
        }
    }
}
