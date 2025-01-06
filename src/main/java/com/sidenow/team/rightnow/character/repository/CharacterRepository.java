package com.sidenow.team.rightnow.character.repository;

import com.sidenow.team.rightnow.character.entity.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Page<Character> findByUserId(Long userId, Pageable pageable);
}

