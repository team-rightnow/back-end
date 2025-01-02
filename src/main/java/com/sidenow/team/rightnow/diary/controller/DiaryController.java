package com.sidenow.team.rightnow.diary.controller;

import com.sidenow.team.rightnow.diary.dto.request.CreateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.request.UpdateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.service.DiaryService;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create")
    public ResponseDto<Void> createDiary(@Valid @RequestBody CreateDiaryRequestDto request, @AuthenticationPrincipal LoginUser loginUser) {
        return diaryService.createDiary(request, loginUser.getUser().getId());
    }

    @PatchMapping("/update/{diaryId}")
    public ResponseDto<Void> updateDiary(
            @PathVariable Long diaryId,
            @Valid @RequestBody UpdateDiaryRequestDto request,
            @AuthenticationPrincipal LoginUser loginUser) {
        return diaryService.updateDiary(diaryId, request, loginUser.getUser().getId());
    }

    @DeleteMapping("/delete/{diaryId}")
    public ResponseDto<Void> deleteDiary(
            @PathVariable Long diaryId,
            @AuthenticationPrincipal LoginUser loginUser) {
        return diaryService.deleteDiary(diaryId, loginUser.getUser().getId());
    }

    @PatchMapping("/restore/{diaryId}")
    public ResponseDto<Void> restoreDiary(
            @PathVariable Long diaryId,
            @AuthenticationPrincipal LoginUser loginUser) {
        return diaryService.restoreDiary(diaryId, loginUser.getUser().getId());
    }
}
