package com.sidenow.team.rightnow.character.repository;

import com.sidenow.team.rightnow.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByUserId(String userId);
    Optional<Character> findById(Long id);
}
