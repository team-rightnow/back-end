package com.sidenow.team.rightnow.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birth;

    @Column(nullable = false)
    private Integer acornCount;

    private String profileImageUrl;

    @Column(nullable = false)
    private boolean deleted = false;

    public void changePassword(String password) {
        this.password = password;
    }

    public void depositAcorn(Integer deposit) {
        this.acornCount += deposit;
    }

    public void withdrawAcorn(Integer withdraw) {
        this.acornCount -= withdraw;
    }
}
