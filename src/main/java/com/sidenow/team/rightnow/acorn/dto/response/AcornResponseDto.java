package com.sidenow.team.rightnow.acorn.dto.response;

import com.sidenow.team.rightnow.acorn.entity.Acorn;
import lombok.Getter;

@Getter
public class AcornResponseDto {

  private Long id;
  private Long userId;
  private Integer balance;
  private Integer deposit;
  private Integer withdraw;

  public AcornResponseDto(Acorn acorn) {
    this.id = acorn.getId();
    this.userId = acorn.getUserId();
    this.balance = acorn.getBalance();
    this.deposit = acorn.getDeposit();
    this.withdraw = acorn.getWithdraw();
  }
}
