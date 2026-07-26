package com.beautystor.security;

import com.beautystor.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import tools.jackson.databind.json.JsonMapper;

@Component
public class SecurityResponseWriter {

    private final JsonMapper objectMapper;

    public SecurityResponseWriter(JsonMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void write(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ApiResponse<?> body = new ApiResponse<>(List.of(new ApiResponse.ErrorItem(message)));
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
