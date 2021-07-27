package com.gokoy.delivery.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gokoy.delivery.domain.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequest {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String nickname;

	public Member toEntity(PasswordEncoder passwordEncoder) {
		return new Member(this.email, passwordEncoder.encode(this.password), this.nickname);
	}
}
