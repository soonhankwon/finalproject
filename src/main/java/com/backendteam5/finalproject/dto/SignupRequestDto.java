package com.backendteam5.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "username이 비어있습니다.")
    private String username;
    @NotBlank(message = "password가 비어있습니다.")
    private String password;
    @NotBlank(message = "area가 비어있습니다.")
    private String area;
    private boolean admin = false;
    private String adminToken = "";
}
