package com.sidenow.team.rightnow.diary.service;

import com.sidenow.team.rightnow.diary.dto.request.CreateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.request.UpdateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.response.DiaryResponseDto;
import com.sidenow.team.rightnow.diary.entity.Diary;
import com.sidenow.team.rightnow.diary.repository.DiaryRepository;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.global.ex.ResponseMessages;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<Void> createDiary(CreateDiaryRequestDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ResponseMessages.INVALID_USER));

        Diary diary = Diary.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .innerPage(request.getInnerPage())
                .temperature(request.getTemperature())
                .deleted(false)
                .build();

        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_CREATED_SUCCESS, null);
    }

    @Transactional
    public ResponseDto<Void> updateDiary(Long diaryId, UpdateDiaryRequestDto request, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomApiException(ResponseMessages.DIARY_NOT_FOUND));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CustomApiException(ResponseMessages.DIARY_PERMISSION_DENIED);
        }

        diary.updateDiary(request.getTitle(), request.getContent(), request.getInnerPage(), request.getTemperature());
        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_UPDATED_SUCCESS, null);
    }

    @Transactional
    public ResponseDto<Void> deleteDiary(Long diaryId, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomApiException(ResponseMessages.DIARY_NOT_FOUND));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CustomApiException(ResponseMessages.DIARY_PERMISSION_DENIED);
        }

        diary.setDeleted(true);
        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_DELETED_SUCCESS, null);
    }

    @Transactional
    public ResponseDto<Void> restoreDiary(Long diaryId, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomApiException(ResponseMessages.DIARY_NOT_FOUND));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CustomApiException(ResponseMessages.DIARY_PERMISSION_DENIED);
        }

        diary.setDeleted(false);
        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_RESTORE_SUCCESS, null);
    }


    @Transactional(readOnly = true)
    public ResponseDto<List<String>> findDiaryDatesByYearAndMonth(Long userId, int year, int month) {
        List<Diary> diaries = diaryRepository.findByUserAndYearMonth(userId, year, month);
        List<String> dates = diaries.stream()
                .map(diary -> diary.getCreatedDate().toLocalDate().toString())
                .distinct()
                .toList();

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_FETCH_SUCCESS, dates);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<DiaryResponseDto>> findByUserIdAndYearMonth(Long userId, int year, int month) {
        List<Diary> diaries = diaryRepository.findByUserAndYearMonth(userId, year, month);
        List<DiaryResponseDto> responseDtos = diaries.stream()
                .map(DiaryResponseDto::from)
                .toList();

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_FETCH_SUCCESS, responseDtos);
    }

    @Transactional(readOnly = true)
    public ResponseDto<DiaryResponseDto> findByUserIdAndDate(Long userId, LocalDate date) {
        List<Diary> diaries = diaryRepository.findByUserIdAndDeletedFalse(userId);

        Diary diary = diaries.stream()
                .filter(d -> d.getCreatedDate().toLocalDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new CustomApiException(ResponseMessages.DIARY_NOT_FOUND));

        return new ResponseDto<>(ResponseDto.SUCCESS, ResponseMessages.DIARY_FETCH_SUCCESS, DiaryResponseDto.from(diary));
    }
}
