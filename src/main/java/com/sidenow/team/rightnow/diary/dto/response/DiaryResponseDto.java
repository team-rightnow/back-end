package com.sidenow.team.rightnow.diary.dto.response;

import com.sidenow.team.rightnow.diary.entity.Diary;
import com.sidenow.team.rightnow.diary.entity.InnerPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class DiaryResponseDto {
    private Long id;
    private String title;
    private String content;
    private Integer temperature;
    private InnerPage innerPage;
    private Boolean deleted;
    private LocalDateTime createdDate;

    public static DiaryResponseDto from(Diary diary) {
        return DiaryResponseDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .temperature(diary.getTemperature())
                .innerPage(diary.getInnerPage())
                .deleted(diary.getDeleted())
                .createdDate(diary.getCreatedDate())
                .build();
    }
}