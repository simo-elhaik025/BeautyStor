package com.beautystor.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête de mise à jour d'utilisateur.")
public class UpdateUserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    // password is optional on update
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    private String firstName;
    private String lastName;

    @NotBlank(message = "Phone is required")
    private String phone;

    private Boolean active;
}
