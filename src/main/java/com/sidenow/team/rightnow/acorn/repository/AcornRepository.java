package com.sidenow.team.rightnow.acorn.repository;

import com.sidenow.team.rightnow.acorn.entity.Acorn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcornRepository extends JpaRepository<Acorn, Long> {

  Page<Acorn> findAllByUserId(Long userId, Pageable pageable);
}
