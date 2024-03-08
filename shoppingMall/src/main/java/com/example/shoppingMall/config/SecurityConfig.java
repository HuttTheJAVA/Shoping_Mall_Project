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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/login","/join","/").permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();
    }

//    @Autowired
//    private CustomUserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http	.csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/login", "/", "/join").permitAll()
//                        .anyRequest().authenticated())
//                // 폼 로그인은 현재 사용하지 않음
//				.formLogin(formLogin -> formLogin
//						.loginPage("/login")
//                        .loginProcessingUrl("/login")
//						.defaultSuccessUrl("/"))
//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true))
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        //        http.
////                authorizeHttpRequests((auth)->auth
//////                                .requestMatchers("/**")
////                                .requestMatchers("/","/login","templates/**","/loginProc","joinProc","/join","/images/**","css/**","js/**").permitAll()
////                                .requestMatchers("/contact").hasAnyRole("ADMIN","USER")
////////
////                );
////        http.
////                formLogin((auth) -> auth
////                        .loginPage("/login")
////                        .loginProcessingUrl("/login") // 이게 MVC의 URL이 아닌 spring security 내부적으로 처리하는 url을 지정하는 거 같음..
////                        .failureUrl("/")
////                        .permitAll()
////                );
////        http.
////                csrf((auth)->auth.disable())
////                .headers((headerConfig) -> headerConfig.frameOptions((frameOptionsConfig -> frameOptionsConfig.disable())));
////        http.securityContext(context->context
////                .requireExplicitSave(false));
//        return http.build();
//    }
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider()throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//        return daoAuthenticationProvider;
//    }
//
////    @Bean
////    public AuthenticationManager authenticationManager() throws Exception{
////        DaoAuthenticationProvider provider = daoAuthenticationProvider();
////        return new ProviderManager(provider);
////    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }




}
