package com.gokoy.delivery.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gokoy.delivery.domain.member.application.MemberServiceForAuth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // (debug = ture) 설정하면 Spring Security에 관한 로그 출력
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberServiceForAuth memberServiceForAuth;

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
			.mvcMatchers("/anyone", "/sign-up", "/sign-in").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class); // 지정된 필터 앞에 커스텀 필터를 추가

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberServiceForAuth);
	}
}
