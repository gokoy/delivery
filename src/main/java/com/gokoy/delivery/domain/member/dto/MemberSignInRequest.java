package com.gokoy.delivery.domain.member.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gokoy.delivery.domain.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignInRequest {

	private String email;

	private String password;

	public Member toEntity(PasswordEncoder passwordEncoder) {
		return new Member(this.email, passwordEncoder.encode(this.password));
	}

}
