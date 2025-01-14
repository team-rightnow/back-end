package com.sidenow.team.rightnow.diary.controller;

import com.sidenow.team.rightnow.diary.dto.request.CreateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.request.UpdateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.response.DiaryResponseDto;
import com.sidenow.team.rightnow.diary.entity.Diary;
import com.sidenow.team.rightnow.diary.service.DiaryService;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.global.ex.ResponseMessages;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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

    @GetMapping("/trash")
    public ResponseDto<List<DiaryResponseDto>> getDeletedDiary(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return diaryService.findAllDeletedDiariesByUser(loginUser.getUser().getId());
    }

    @GetMapping("/dates/{year}/{month}")
    public ResponseDto<List<String>> getDiaryDatesByMonth(
            @PathVariable int year,
            @PathVariable int month,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        if (month < 1 || month > 12) throw new CustomApiException(ResponseMessages.INVALID_MONTH);

        return diaryService.findDiaryDatesByYearAndMonth(loginUser.getUser().getId(), year, month);
    }

    @GetMapping("/month/{year}/{month}")
    public ResponseDto<List<DiaryResponseDto>> getDiaryByMonth(
            @PathVariable int year,
            @PathVariable int month,
            @AuthenticationPrincipal LoginUser loginUser) {
        if (month < 1 || month > 12) throw new CustomApiException(ResponseMessages.INVALID_MONTH);

        return diaryService.findByUserIdAndYearMonth(loginUser.getUser().getId(), year, month);
    }

    @GetMapping("/{date}")
    public ResponseDto<DiaryResponseDto> getDiaryByDate(
            @PathVariable String date,
            @AuthenticationPrincipal LoginUser loginUser) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            return diaryService.findByUserIdAndDate(loginUser.getUser().getId(), parsedDate);
        } catch (DateTimeParseException e) {
            throw new CustomApiException("잘못된 날짜 형식입니다. (yyyy-MM-dd)");
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseDto<List<DiaryResponseDto>> getDiaryByKeyword(
            @PathVariable String keyword,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return diaryService.findByUserIdAndKeyword(loginUser.getUser().getId(), keyword);
    }
}
