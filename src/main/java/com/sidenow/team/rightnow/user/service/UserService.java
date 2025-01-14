package com.sidenow.team.rightnow.user.service;

import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.dto.request.BirthChangeRequestDto;
import com.sidenow.team.rightnow.user.dto.request.PasswordChangeRequestDto;
import com.sidenow.team.rightnow.user.dto.response.UserResponseDto;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(
                ()-> new CustomApiException("존재하지 않는 userId 입니다.")
        );
        return new UserResponseDto(user);
    }

    @Transactional
    public void modifyUserPassword(Long id, PasswordChangeRequestDto passwordChangeRequestDto) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(
                ()-> new CustomApiException("존재하지 않는 userId 입니다.")
        );

        if (!passwordEncoder.matches(passwordChangeRequestDto.getNowPassword(), user.getPassword())) {
            throw new CustomApiException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.changePassword(passwordEncoder.encode(passwordChangeRequestDto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void modifyUserBirth(Long id, BirthChangeRequestDto birthChangeRequestDto) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(
                ()-> new CustomApiException("존재하지 않는 userId 입니다.")
        );

        user.changeBirth(birthChangeRequestDto.getBirth());
    }
}