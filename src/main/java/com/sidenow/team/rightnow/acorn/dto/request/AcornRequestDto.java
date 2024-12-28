package com.sidenow.team.rightnow.acorn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AcornRequestDto {

  @NotNull
  private Integer balance;

  @NotBlank

  private String actions;
}
