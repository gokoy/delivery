package com.gokoy.delivery.global.config.security;

import com.gokoy.delivery.domain.consumer.application.ConsumerServiceForAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity // (debug = ture) 설정하면 Spring Security에 관한 로그 출력
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ConsumerServiceForAuth consumerServiceForAuth;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable() // Basic Auth 사용 안함
                .csrf()
                .disable() // CSRF 보호 사용 안함
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 인증을 사용하기 때문에 Session 사용 안함
                .and()
                .authorizeRequests()
                .mvcMatchers("/consumers/sign-up", "/consumers/sign-in").permitAll()
                .mvcMatchers("/ceos/sign-up", "/ceos/sign-in").permitAll()
                .mvcMatchers("/v2/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); // 지정된 필터 앞에 커스텀 필터를 추가

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(consumerServiceForAuth);
    }
}
