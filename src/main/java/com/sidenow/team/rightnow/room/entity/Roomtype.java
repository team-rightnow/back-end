package com.sidenow.team.rightnow.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Roomtype {
    RED("RED"),
    YELLOW("YELLOW"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    PURPLE("PURPLE"),
    PINK("PINK"),
    BROWN("BROWN"),
    BLACK("BLACK");

    private String room;
}
