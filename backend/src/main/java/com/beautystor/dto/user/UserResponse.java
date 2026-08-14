package com.beautystor.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation d'un utilisateur.")
public class UserResponse {
    private long id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean active;
}
