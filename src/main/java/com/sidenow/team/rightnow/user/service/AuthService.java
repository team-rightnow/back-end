package com.sidenow.team.rightnow.user.service;

import com.sidenow.team.rightnow.global.ResponseDto;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.dto.request.CreateUserRequestDto;
import com.sidenow.team.rightnow.user.dto.response.CreateUserResponseDto;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.entity.UserRole;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public CreateUserResponseDto createUser(CreateUserRequestDto request) {
    // 이메일 중복확인
    if(userRepository.findByEmailAndDeletedFalse(request.getEmail()).isPresent()) {
      throw new CustomApiException("중복된 이메일입니다.");
    }
    // 닉네임 중복확인
    if(userRepository.findByNicknameAndDeletedFalse(request.getNickname()).isPresent()) {
      throw new CustomApiException("중복된 닉네임입니다.");
    }

    User user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .nickname(request.getNickname())
        .userRole(UserRole.MEMBER)
        .birth(request.getBirth())
        .acornCount(10)
        .deleted(false)
        .build();

    User savedUser = userRepository.save(user);
    return new CreateUserResponseDto(savedUser);
  }

}
