 package com.example.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest (
    @NotBlank(message="email required") 
    @Email
    String email,

    @NotBlank(message="password required") 
    @Size(min=6, message="password is too short")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Mín 6 chars, 1 mayús, 1 minús, 1 número, 1 especial")
    String password
) {}
