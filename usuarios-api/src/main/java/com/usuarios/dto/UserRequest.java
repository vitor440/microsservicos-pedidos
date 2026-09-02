package com.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
}
