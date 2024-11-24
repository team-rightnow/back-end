package com.sidenow.team.rightnow.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.entity.UserRole;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;

@Slf4j
public class JwtProcess {

    // 토큰 생성
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("authentication")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))
                .withClaim("userId", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getUserRole()+"") // 문자열 캐스팅
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        log.info("토큰 생성 확인 = "+ JwtVO.TOKEN_PREFIX+jwtToken);
        return JwtVO.TOKEN_PREFIX+jwtToken;
    }

    // 토큰 검증
    // return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할거임. (강제 로그인)
    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("userId").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).userRole(UserRole.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);
        log.info("토큰 검증 확인 = "+ loginUser.getUser().getNickname());
        return loginUser;
    }
}
