package com.api.twstock.model.DTO;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CreateUserDto {

    private String username;

    private String password;

    private String email;
}
