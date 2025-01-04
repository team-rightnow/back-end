package com.sidenow.team.rightnow.security.config.jwt;

/*
원래 SECRET 은 노출되면 안됨. 보통 (클라우드 AWS - 환경 변수 사용 또는 파일에 있는 것을 읽어서 불러옴)
 */
public interface JwtVO {
    String SECRET = "서버키(임시)";
    int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 만료시간은 일주일 (나중에 하루로 설정할 것임)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER = "Authorization";


}
