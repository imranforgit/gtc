package com.wcl.gtc.dto;

import com.wcl.gtc.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Name is required")
        private String name;
        @NotBlank
        @Email(message = "Invalid email format")
        private String email;
        @NotBlank(message = "Password is required")
        private String password;
        @NotBlank(message = "Role is required")
        private Role role;
    }