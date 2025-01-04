package com.sidenow.team.rightnow.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserResponseDto {

    private String email;
    private String nickname;
    private String userRole;
}
