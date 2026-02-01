package com.divyansh.linkedin.user_service.dto;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String name;
}
