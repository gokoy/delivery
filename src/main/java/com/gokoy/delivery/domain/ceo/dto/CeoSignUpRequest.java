package com.gokoy.delivery.domain.ceo.dto;

import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.consumer.domain.Consumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CeoSignUpRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String phone;

    public Ceo toEntity(PasswordEncoder passwordEncoder) {
        return new Ceo(this.email, passwordEncoder.encode(this.password), this.phone);
    }
}
