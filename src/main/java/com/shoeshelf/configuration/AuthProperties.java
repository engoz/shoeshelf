package com.shoeshelf.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthProperties {
    @NotBlank
    private String user;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}
