package com.sidenow.team.rightnow.user.dto.request;

import com.sidenow.team.rightnow.user.entity.User;
import lombok.Getter;

@Getter

public class LoginUserRequestDto {

    private String email;
    private String password;

}
