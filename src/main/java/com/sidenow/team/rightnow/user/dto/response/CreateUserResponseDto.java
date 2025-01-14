package com.sidenow.team.rightnow.user.dto.response;

import com.sidenow.team.rightnow.user.entity.User;
import lombok.Getter;

@Getter
public class CreateUserResponseDto {
    private Long userId;

    public CreateUserResponseDto(User user) {
        this.userId = user.getId();
    }
}