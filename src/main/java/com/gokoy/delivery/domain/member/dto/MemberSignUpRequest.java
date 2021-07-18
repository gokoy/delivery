package com.gokoy.delivery.domain.member.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

	private List<String> roles = new ArrayList<>();

	public Member toEntity(PasswordEncoder passwordEncoder) {
		return new Member(this.email, passwordEncoder.encode(this.password), Collections.singletonList("NORMAL"));
	}

	@Override
	public String toString() {
		return "MemberSignUpRequest{" +
			"email='" + email + '\'' +
			", password='" + password + '\'' +
			", roles=" + roles +
			'}';
	}
}
