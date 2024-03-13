package com.example.shoppingMall.config;

import com.example.shoppingMall.domain.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/login", "/join","/","/resources/**","/**").permitAll() //TODO 아이템 이미지 렌더링 문제 해결하면 /** 지우기
                .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")    // 해당 url을 GetMapping으로 가지는 컨트롤러로 요청이 보내진다.
                        .loginProcessingUrl("/login") // 이 url을 PostMapping으로 가지는 컨트롤러로 보내는 것이 아닌 이 url로 오는 요청에 대해 Spring Security 인증을 한다는 것이다. 즉 postMapping("/login") 컨트롤러로 가지 않고 Spring security가 "/login" 경로로 오는 요청을 가로채 인증을 수행한다는 말이다.
                        .defaultSuccessUrl("/")
                        .usernameParameter("userId")
                        .permitAll());

        http.logout(logout->logout.logoutSuccessUrl("/").invalidateHttpSession(true));

        http
                .sessionManagement((auth)->auth
                        .maximumSessions(1) // 한 계정에 최대 허용 세션 수 = 다중 접속 허용 수
                        .maxSessionsPreventsLogin(false)); // 허용 세션 수 초과 시 true: 새로운 로그인 차단, false: 기존 세션 하나 삭제 = 기존 로그인 사용자가 로그아웃 됨.

        http.csrf(csrf->csrf.disable());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}