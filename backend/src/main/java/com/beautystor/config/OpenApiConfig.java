package com.beautystor.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.core.converter.ModelConverters;
import java.util.Map;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BeautyStor API",
                description = "API backend de BeautyStor.",
                version = "1.0"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer commonErrorResponsesCustomizer() {
        return openApi -> {
            // Ensure ApiResponse schema is present in components so $ref links resolve.
            Components components = openApi.getComponents();
            if (components == null) {
                components = new Components();
                openApi.setComponents(components);
            }
            try {
                Map<String, Schema> schemas = ModelConverters.getInstance().read(com.beautystor.common.ApiResponse.class);
                if (schemas != null) {
                    schemas.forEach(components::addSchemas);
                }
            } catch (Exception ignored) {
                // If model conversion fails, don't block OpenAPI generation; errors will surface in logs.
            }

            if (openApi.getPaths() == null) {
                return;
            }

            openApi.getPaths().values().stream()
                    .flatMap(pathItem -> pathItem.readOperations().stream())
                    .forEach(operation -> {
                        addResponseIfAbsent(operation, "400", "Requête invalide");
                        addResponseIfAbsent(operation, "404", "Ressource introuvable");
                        addResponseIfAbsent(operation, "500", "Erreur interne");

                        if (operation.getSecurity() != null && !operation.getSecurity().isEmpty()) {
                            addResponseIfAbsent(operation, "401", "Authentification requise");
                            addResponseIfAbsent(operation, "403", "Accès interdit");
                        }
                    });
        };
    }

    private void addResponseIfAbsent(io.swagger.v3.oas.models.Operation operation, String code, String description) {
        if (operation.getResponses() != null && operation.getResponses().containsKey(code)) {
            return;
        }

        ApiResponses responses = operation.getResponses() == null ? new ApiResponses() : operation.getResponses();
        responses.addApiResponse(code, new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json", new MediaType()
                        .schema(new Schema<>().$ref("#/components/schemas/ApiResponse")))));
        operation.setResponses(responses);
    }
}
