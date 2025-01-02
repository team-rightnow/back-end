package com.sidenow.team.rightnow.diary.dto.response;

import com.sidenow.team.rightnow.diary.entity.InnerPage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String title;
    private String content;
    private Integer temperature;
    private InnerPage innerPage;
    private Boolean deleted;
}