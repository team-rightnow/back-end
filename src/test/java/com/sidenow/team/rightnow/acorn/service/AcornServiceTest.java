package com.sidenow.team.rightnow.acorn.service;

import static org.mockito.Mockito.when;

import com.sidenow.team.rightnow.acorn.entity.Acorn;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.entity.UserRole;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AcornServiceTest {

  @InjectMocks
  private AcornService acornService;

  @Mock
  private AcornRepository acornRepository;

  @Mock
  private UserRepository userRepository;

  @Test
  void depositAcorn() {
    // given
    Long userId = 1L;
    Integer deposit = 10;

    // stub 1
    User user = User.builder()
        .id(userId)
        .email("test@test.com")
        .password("테스트")
        .nickname("테스트")
        .userRole(UserRole.MEMBER)
        .birth(LocalDate.now())
        .acornCount(10)
        .deleted(false)
        .build();
    when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(Optional.of(user));

    // stub 2
    Acorn acorn = Acorn.builder()
        .userId(user.getId())
        .balance(user.getAcornCount() + deposit)
        .deposit(deposit)
        .withdraw(0)
        .build();
    when(acornRepository.save(ArgumentMatchers.any(Acorn.class))).thenReturn(acorn);

    // when
    acornService.depositAcorn(userId, deposit);

    // then
    Assertions.assertEquals(user.getAcornCount(), 20);
  }

  @Test
  void withdrawAcorn() {
    // given
    Long userId = 1L;
    Integer withdraw = 5;

    // stub 1
    User user = User.builder()
        .id(userId)
        .email("test@test.com")
        .password("테스트")
        .nickname("테스트")
        .userRole(UserRole.MEMBER)
        .birth(LocalDate.now())
        .acornCount(10)
        .deleted(false)
        .build();
    when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(Optional.of(user));

    // stub 2
    Acorn acorn = Acorn.builder()
        .userId(user.getId())
        .balance(user.getAcornCount() - withdraw)
        .deposit(0)
        .withdraw(withdraw)
        .build();
    when(acornRepository.save(ArgumentMatchers.any(Acorn.class))).thenReturn(acorn);

    // when
    acornService.withdrawAcorn(userId, withdraw);

    // then
    Assertions.assertEquals(user.getAcornCount(), 5);
  }
}