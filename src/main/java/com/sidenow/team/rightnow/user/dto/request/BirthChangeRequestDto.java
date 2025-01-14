package com.sidenow.team.rightnow.user.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class BirthChangeRequestDto {

  @Past(message = "생년월일은 과거 날짜여야 합니다.")
  @Temporal(TemporalType.DATE)
  private LocalDate birth;

}
