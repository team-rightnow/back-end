package com.sidenow.team.rightnow.acorn.service;

import com.sidenow.team.rightnow.acorn.dto.response.AcornResponseDto;
import com.sidenow.team.rightnow.acorn.entity.Acorn;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcornService {

  private final AcornRepository acornRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public AcornResponseDto getAcorn(Long userId) {
    User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
        () -> new CustomApiException("존재하지 않는 userId 입니다.")
    );
    return new AcornResponseDto(user.getAcornCount());
  }

  @Transactional
  public void depositAcorn(Long userId, Integer deposit) {
    User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
        () -> new CustomApiException("존재하지 않는 userId 입니다.")
    );
    user.depositAcorn(deposit);

    Acorn acorn = Acorn.builder()
        .userId(user.getId())
        .balance(user.getAcornCount())
        .deposit(deposit)
        .withdraw(0)
        .build();

    acornRepository.save(acorn);
  }

  @Transactional
  public void withdrawAcorn(Long userId, Integer withdraw) {
    User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
        () -> new CustomApiException("존재하지 않는 userId 입니다.")
    );
    user.withdrawAcorn(withdraw);

    Acorn acorn = Acorn.builder()
        .userId(user.getId())
        .balance(user.getAcornCount())
        .deposit(0)
        .withdraw(withdraw)
        .build();

    acornRepository.save(acorn);
  }

}
