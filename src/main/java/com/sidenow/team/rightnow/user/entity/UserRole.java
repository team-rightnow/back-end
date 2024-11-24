package com.sidenow.team.rightnow.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Getter
@AllArgsConstructor
public enum UserRole {
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private String role;
}
