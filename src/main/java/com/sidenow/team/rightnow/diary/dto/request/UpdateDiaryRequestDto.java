package com.sidenow.team.rightnow.diary.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class UpdateDiaryRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Min(value = -9, message = "온도는 -9도 이상이어야 합니다.")
    @Max(value = 50, message = "온도는 50도 이하여야 합니다.")
    private Integer temperature;
}

