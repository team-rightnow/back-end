package com.sidenow.team.rightnow.user.dto.response;

import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.entity.UserRole;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UserResponseDto {

  private Long id;
  private String email;
  private String password;
  private String nickname;
  private UserRole userRole;
  private LocalDate birth;
  private String profileImageUrl;
  private boolean deleted;

  public UserResponseDto(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.nickname = user.getNickname();
    this.userRole = user.getUserRole();
    this.birth = user.getBirth();
    this.profileImageUrl = user.getProfileImageUrl();
    this.deleted = user.isDeleted();
  }
}
