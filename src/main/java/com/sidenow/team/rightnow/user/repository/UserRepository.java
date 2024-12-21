package com.sidenow.team.rightnow.user.repository;

import com.sidenow.team.rightnow.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndDeletedFalse(String email);
    Optional<User> findByNicknameAndDeletedFalse(String nickname);
}
