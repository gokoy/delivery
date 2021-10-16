package com.gokoy.delivery.domain.ceo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CeoSignInRequest {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
