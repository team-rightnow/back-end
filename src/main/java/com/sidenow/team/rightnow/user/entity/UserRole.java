package com.sidenow.team.rightnow.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private String role;
}
