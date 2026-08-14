package com.beautystor.controller;

import com.beautystor.dto.statistics.AdminStatisticsResponse;
import com.beautystor.dto.statistics.DailyOrderStatResponse;
import com.beautystor.dto.statistics.OrderStatusCountResponse;
import com.beautystor.dto.statistics.TopSoldProductResponse;
import com.beautystor.enm.StatisticsPeriod;
import com.beautystor.security.JwtAuthenticationFilter;
import com.beautystor.service.AdminStatisticsService;
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
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = AdminStatisticsController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class))
@Import(AdminStatisticsControllerTest.TestBeans.class)
class AdminStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestAdminStatisticsService testAdminStatisticsService;

    @BeforeEach
    void resetState() {
        testAdminStatisticsService.reset();
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "ADMIN")
    void adminCanAccessStatisticsForWeek() throws Exception {
        LocalDate today = LocalDate.now();
        testAdminStatisticsService.result = new AdminStatisticsResponse(
                today.minusDays(29),
                today,
                2L,
                new BigDecimal("30.00"),
                List.of(
                        new DailyOrderStatResponse(today.minusDays(29).getYear(), today.minusDays(29).getMonthValue(), today.minusDays(29).getDayOfMonth(), 2L),
                        new DailyOrderStatResponse(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 0L)
                ),
                List.of(
                        new OrderStatusCountResponse(com.beautystor.enm.OrderStatus.PENDING, 4L),
                        new OrderStatusCountResponse(com.beautystor.enm.OrderStatus.DELIVERED, 7L),
                        new OrderStatusCountResponse(com.beautystor.enm.OrderStatus.CANCELLED, 1L)
                ),
                List.of(new TopSoldProductResponse(11L, "Shampoo", 9L)));

        mockMvc.perform(get("/api/admin/statistics").param("period", StatisticsPeriod.WEEK.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.periodStart").value(today.minusDays(29).toString()))
                .andExpect(jsonPath("$.data.periodEnd").value(today.toString()))
                .andExpect(jsonPath("$.data.totalOrdersInPeriod").value(2))
                .andExpect(jsonPath("$.data.deliveredRevenueInPeriod").value(30.0))
                .andExpect(jsonPath("$.data.ordersByDay[0].orderCount").value(2))
                .andExpect(jsonPath("$.data.orderStatuses[1].status").value("DELIVERED"))
                .andExpect(jsonPath("$.data.topProducts[0].productName").value("Shampoo"));
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "ADMIN")
    void adminCanAccessStatisticsForMonth() throws Exception {
        LocalDate today = LocalDate.now();
        testAdminStatisticsService.result = new AdminStatisticsResponse(
                today.minusDays(29),
                today,
                1L,
                new BigDecimal("12.00"),
                List.of(new DailyOrderStatResponse(today.minusDays(29).getYear(), today.minusDays(29).getMonthValue(), today.minusDays(29).getDayOfMonth(), 1L)),
                List.of(new OrderStatusCountResponse(com.beautystor.enm.OrderStatus.PENDING, 4L)),
                List.of(new TopSoldProductResponse(11L, "Shampoo", 9L)));

        mockMvc.perform(get("/api/admin/statistics").param("period", StatisticsPeriod.MONTH.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.periodStart").value(today.minusDays(29).toString()))
                .andExpect(jsonPath("$.data.totalOrdersInPeriod").value(1))
                .andExpect(jsonPath("$.data.deliveredRevenueInPeriod").value(12.0));
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "ADMIN")
    void adminCanAccessStatisticsForYear() throws Exception {
        LocalDate today = LocalDate.now();
        testAdminStatisticsService.result = new AdminStatisticsResponse(
                today.minusMonths(11).withDayOfMonth(1),
                today,
                12L,
                new BigDecimal("120.00"),
                List.of(new DailyOrderStatResponse(today.minusMonths(11).getYear(), today.minusMonths(11).getMonthValue(), 1, 3L)),
                List.of(new OrderStatusCountResponse(com.beautystor.enm.OrderStatus.DELIVERED, 7L)),
                List.of(new TopSoldProductResponse(11L, "Shampoo", 9L)));

        mockMvc.perform(get("/api/admin/statistics").param("period", StatisticsPeriod.YEAR.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.periodStart").value(today.minusMonths(11).withDayOfMonth(1).toString()))
                .andExpect(jsonPath("$.data.totalOrdersInPeriod").value(12))
                .andExpect(jsonPath("$.data.deliveredRevenueInPeriod").value(120.0));
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "USER")
    void userGets403() throws Exception {
        mockMvc.perform(get("/api/admin/statistics"))
                .andExpect(status().isForbidden());
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "DELIVERY_AGENT")
    void deliveryAgentGets403() throws Exception {
        mockMvc.perform(get("/api/admin/statistics"))
                .andExpect(status().isForbidden());
    }

    @Test
    void nonAuthenticatedGets401() throws Exception {
        mockMvc.perform(get("/api/admin/statistics"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(roles = "ADMIN")
    void invalidPeriodReturns400() throws Exception {
        mockMvc.perform(get("/api/admin/statistics").param("period", "INVALID"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Invalid period"));
    }

    @TestConfiguration
    static class TestBeans {

        @Bean
        TestAdminStatisticsService testAdminStatisticsService() {
            return new TestAdminStatisticsService();
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
                            .requestMatchers("/api/admin/statistics").hasRole("ADMIN")
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

    static class TestAdminStatisticsService implements AdminStatisticsService {
        private AdminStatisticsResponse result;

        void reset() {
            result = null;
        }

        @Override
        public AdminStatisticsResponse getStatistics(StatisticsPeriod period) {
            return result;
        }
    }
}
