package com.sidenow.team.rightnow.acorn.service;

import com.sidenow.team.rightnow.acorn.dto.response.AcornResponseDto;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornService {

  private final AcornRepository acornRepository;
  private final UserRepository userRepository;

  public AcornResponseDto getAcorn(Long userId) {
    User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
        () -> new CustomApiException("존재하지 않는 userId 입니다.")
    );
    return new AcornResponseDto(user.getAcornCount());
  }

  //public void depositAcorn(Long userId, )
}
