package com.example.dailymanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * 비밀번호 해싱을 위한 PasswordEncoder 빈 등록. BCryptPasswordEncoder를 사용하여 비밀번호를 안전하게 해싱한다.
     * 비밀번호 대조시에도 이 PasswordEncoder를 사용하여 해싱된 비밀번호와 입력된 비밀번호를 비교할 수 있다.
     *
     * @return PasswordEncoder 빈 객체, BCryptPasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 기본적인 필터 체인 설정 메서드. CSRF 보호를 비활성화하고, "/error"와 "/events/**" 경로에 대한 모든 요청을 허용하며, 그 외의 모든 요청은 거부한다.
     *
     * @param http 인증/인가 규칙을 구성하고 `SecurityFilterChain`을 빌드하는 데 사용하는 `HttpSecurity` 객체
     * @return 필터체인 객체, SecurityFilterChain 인스턴스
     * @throws Exception 필터 체인 빌드 과정에서 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  /* Maybe remove this line as this api did not decide to use JWT yet */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error", "/events/**").permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }
}
