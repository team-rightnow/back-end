package com.sidenow.team.rightnow.diary.service;

import com.sidenow.team.rightnow.diary.dto.request.CreateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.request.UpdateDiaryRequestDto;
import com.sidenow.team.rightnow.diary.dto.response.DiaryResponseDto;
import com.sidenow.team.rightnow.diary.entity.Diary;
import com.sidenow.team.rightnow.diary.repository.DiaryRepository;
import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
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
                .orElseThrow(() -> new CustomApiException("유효하지 않는 사용자입니다."));

        Diary diary = Diary.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .innerPage(request.getInnerPage())
                .temperature(request.getTemperature())
                .deleted(false)
                .build();

        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, "다이어리가 생성되었습니다.", null);
    }

    @Transactional
    public ResponseDto<Void> updateDiary(Long diaryId, UpdateDiaryRequestDto request, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomApiException("다이어리를 찾을 수 없습니다."));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CustomApiException("수정 권한이 없습니다.");
        }

        diary.updateDiary(request.getTitle(), request.getContent(), request.getInnerPage(), request.getTemperature());
        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, "다이어리가 수정되었습니다.", null);
    }

    @Transactional
    public ResponseDto<Void> deleteDiary(Long diaryId, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomApiException("다이어리를 찾을 수 없습니다."));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CustomApiException("삭제 권한이 없습니다.");
        }

        diary.setDeleted(true);
        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, "다이어리가 삭제되었습니다.", null);
    }

    @Transactional
    public ResponseDto<Void> restoreDiary(Long diaryId, Long userId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomApiException("다이어리를 찾을 수 없습니다."));

        if (!diary.getUser().getId().equals(userId)) {
            throw new CustomApiException("복구 권한이 없습니다.");
        }

        diary.setDeleted(false);
        diaryRepository.save(diary);

        return new ResponseDto<>(ResponseDto.SUCCESS, "다이어리가 복구되었습니다.", null);
    }

    @Transactional(readOnly = true)
    public ResponseDto<DiaryResponseDto> findByUserIdAndDate(Long userId, LocalDate date) {
        List<Diary> diaries = diaryRepository.findByUserIdAndDeletedFalse(userId);

        Diary diary = diaries.stream()
                .filter(d -> d.getCreatedDate().toLocalDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new CustomApiException("해당 날짜의 다이어리가 없습니다."));

        return new ResponseDto<>(ResponseDto.SUCCESS, "다이어리 조회에 성공했습니다.", DiaryResponseDto.from(diary));
    }
}
