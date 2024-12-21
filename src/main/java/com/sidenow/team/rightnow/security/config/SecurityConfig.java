package com.sidenow.team.rightnow.security.config;

import com.sidenow.team.rightnow.global.ex.CustomResponseUtil;
import com.sidenow.team.rightnow.security.config.jwt.JwtAuthenticationFilter;
import com.sidenow.team.rightnow.security.config.jwt.JwtAuthorizationFilter;
import com.sidenow.team.rightnow.user.entity.UserRole;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 등록이 필요함.
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); // AuthenticationManager 에 접근 가능.
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }

        public HttpSecurity build(){
            return getBuilder();
        }
    }

    // JWT 서버를 만들 예정. 그래서 Session 사용 안할거임.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        http.csrf(cf->cf.disable());

        // jSessionId를 서버쪽에서 관리 안 하겠다는 뜻
        http.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // react, 앱 사용해서 요청할 예정.
        // 백엔드에서 HTML 로그인 페이지를 제공하지 않고 REST API 방식으로 로그인 처리를 할것임.
        http.formLogin(f->f.disable());

        // httpBasic 은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다. -> 비허용 할것임.
        http.httpBasic(hb->hb.disable());

        // 필터 적용
        http.with(new CustomSecurityFilterManager(), c-> c.build());

        // 로그아웃
        http.logout(logout -> logout
            .logoutUrl("/api/auth/logout")
            .logoutSuccessUrl("/api/auth/login")
            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())

        );

        http.authorizeHttpRequests(c ->
                        c
                                .requestMatchers("/api/auth/signup").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .anyRequest().authenticated()
        );

        // Exception 처리
        // 인증 실패
        http.exceptionHandling(e -> e.authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요.", HttpStatus.UNAUTHORIZED);
        }));

        // 권한 실패
        http.exceptionHandling(e -> e.accessDeniedHandler((request, response, accessDeniedException) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        }));

        return http.build();
    }
}
