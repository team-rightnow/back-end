package com.sidenow.team.rightnow.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserResponseDto {

    private Long userId;
    private String email;
    private String nickname;
    private String userRole;
}
