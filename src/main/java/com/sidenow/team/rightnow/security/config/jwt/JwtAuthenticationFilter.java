package com.sidenow.team.rightnow.security.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidenow.team.rightnow.global.ex.CustomResponseUtil;
import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import com.sidenow.team.rightnow.user.dto.response.LoginUserResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");
        this.authenticationManager = authenticationManager;
    }

    // Post : /login 일 때 동작함.
    // 하지만 /api/login 으로 바꾸고 싶다면?
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            LoginUserRequestDto loginUserRequestDto = om.readValue(request.getInputStream(), LoginUserRequestDto.class);

            // 강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUserRequestDto.getEmail(), loginUserRequestDto.getPassword());

            // UserDetailsService loadUserByUsername 호출
            // JWT 를 쓴다 하더라도, 컨트롤러 진입을 하면 시큐리티의 권한체크, 인증체크의 도움을 받을 수 있게 세션을 만든다.
            // 이 세션의 유효기간은 request 하고, response 하면 끝!! (임시)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        } catch (Exception e) {
            // unsuccessfulAuthentication 호출함.
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        CustomResponseUtil.fail(response, "로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED);
    }

    // return authentication 잘 작동하면 successfulAuthentication 실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(JwtVO.HEADER, jwtToken);

        LoginUserResponseDto responseDto = LoginUserResponseDto.builder()
                .email(loginUser.getUser().getEmail())
                .nickname(loginUser.getUser().getNickname())
                .userRole(loginUser.getUser().getUserRole().getRole())
                .build();
        CustomResponseUtil.success(response, responseDto);

    }
}