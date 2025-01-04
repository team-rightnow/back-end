package com.sidenow.team.rightnow.security.config.auth;

import com.sidenow.team.rightnow.user.entity.User;
import com.sidenow.team.rightnow.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private UserRepository userRepository;

    // 시큐리티로 로그인이 될 때, 시큐리티가 loadUserByUsername() 실행해서 username 을 체크할 수 있음.
    // 없으면 오류고
    // 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new InternalAuthenticationServiceException("유저를 찾을 수 없습니다.")); // 나중에 테스트할 때 설명
        return new LoginUser(user); // 찾았으면, 이 객체가 세션에 만들어짐.
    }
}
