package com.sidenow.team.rightnow.user.dto.request;

import com.sidenow.team.rightnow.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter

public class LoginUserRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
