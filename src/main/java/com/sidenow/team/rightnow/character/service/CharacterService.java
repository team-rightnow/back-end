package com.sidenow.team.rightnow.character.service;

import com.sidenow.team.rightnow.acorn.dto.response.AcornCountResponseDto;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.character.entity.Character;
import com.sidenow.team.rightnow.character.repository.CharacterRepository;
import com.sidenow.team.rightnow.character.dto.CharacterDTO;
import com.sidenow.team.rightnow.global.ex.CustomApiException;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import com.sidenow.team.rightnow.acorn.repository.AcornRepository;
import com.sidenow.team.rightnow.acorn.service.AcornService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final AcornRepository acornRepository;
    private final AcornService acornService;

    @Transactional
    public void createCharacter(Long userId, CharacterDTO characterDTO) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userId 입니다."));

        acornService.withdrawAcorn(userId, 2);

        Character character = Character.builder()
                .character(characterDTO.getCharacter())
                .user(user)
                .build();

        characterRepository.save(character);
    }

    @Transactional(readOnly = true)
    public Page<CharacterDTO> getCharacter(Long userId, Pageable pageable) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 userId 입니다."));

        return characterRepository.findByUserId(userId, pageable)
                .map(CharacterDTO::fromEntity);
    }


    @Transactional(readOnly = true)
    public Character updateCharacter(Long id, CharacterDTO newCharacterDTO) {
        Optional<Character> existingCharacter = characterRepository.findById(id);
        if (existingCharacter.isPresent()) {
            User user = userRepository.findByIdAndDeletedFalse(newCharacterDTO.getUserId())
                    .orElseThrow(() -> new CustomApiException("존재하지 않는 userID 입니다."));

            acornService.withdrawAcorn(user.getId(), 2);

            Character character = existingCharacter.get();
            character.update(new Character(newCharacterDTO.getCharacter(), user));
            return characterRepository.save(character);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean deleteCharacter(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        if (character.isPresent()) {
            characterRepository.delete(character.get());
            return true;
        }
        return false;
    }


}

