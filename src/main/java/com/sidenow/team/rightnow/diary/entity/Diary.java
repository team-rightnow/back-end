package com.sidenow.team.rightnow.diary.entity;

import com.sidenow.team.rightnow.global.BaseEntity;
import com.sidenow.team.rightnow.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "diary")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @Setter
    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(nullable = false)
    private Integer temperature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InnerPage innerPage;

    public void updateDiary(String title, String content, InnerPage innerPage, Integer temperature) {
        this.title = title;
        this.content = content;
        this.innerPage = innerPage;
        this.temperature = temperature;
    }
}
