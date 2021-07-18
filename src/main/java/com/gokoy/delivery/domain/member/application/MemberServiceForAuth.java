package com.gokoy.delivery.domain.member.application;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gokoy.delivery.domain.member.dao.MemberRepository;
import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceForAuth implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomEntityNotFoundException("email", ErrorCode.EMAIL_NOT_FOUND));

		return User.builder()
			.username(member.getUsername())
			.password(member.getPassword())
			.roles(member.getRoles().toArray(new String[0]))
			.build();
	}

}
