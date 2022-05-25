package com.api.twstock.model.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;

    private String password;
}
