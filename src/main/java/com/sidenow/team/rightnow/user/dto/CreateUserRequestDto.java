package com.sidenow.team.rightnow.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class CreateUserRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 이하이어야 합니다.")
    private String nickname;

    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    @Temporal(TemporalType.DATE)
    private LocalDate birth;







}
