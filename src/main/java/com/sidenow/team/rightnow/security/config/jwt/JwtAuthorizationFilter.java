package com.sidenow.team.rightnow.security.config.jwt;

import com.sidenow.team.rightnow.security.config.auth.LoginUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 모든 주소에서 동작함 (토큰 검증)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // jwt 토큰 헤더를 추가하지 않아도 해당 필터를 통과를 할 순 있지만, 결국 시큐리티단에서 세션 값 검증에 실패함.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isHeaderVerify(request, response)) {
            // 토큰이 존재함
            String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
            LoginUser loginUser = JwtProcess.verify(token);
            // -> 토큰 검증 완료

            // 임시 세션 (UserDetails 타입 or username) 지금은 UserDetails 를 넣었음 = loginUser
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); // 강제 로그인 진행
            // -> 임시 세션이 생성됨
        }
        chain.doFilter(request, response); // 다 됐으면 체인 타서 계속 진행

        // <** 이렇게 토큰이 있으면 세션 생성 후 체인타고, 없으면 그대로 체인타서 어차피 시큐리티 컨피그에 에러 잡힘 **>
        // 이렇게 짜야 테스트할 때 시큐리티 세션만 주입(@WithUserDetails) 받아서 사용할 수 있으므로 편리해짐.
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JwtVO.HEADER);
        if (header == null || !header.startsWith(JwtVO.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}