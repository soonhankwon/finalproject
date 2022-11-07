package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String area;
    private boolean admin = false;
    private String adminToken = "";
}
