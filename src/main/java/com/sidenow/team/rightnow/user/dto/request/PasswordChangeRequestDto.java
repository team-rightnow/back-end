package com.sidenow.team.rightnow.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordChangeRequestDto {

  @NotBlank
  private String nowPassword;
  @NotBlank
  private String newPassword;
}
