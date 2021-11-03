package com.gokoy.delivery.domain.ceo.application;

import com.gokoy.delivery.domain.ceo.dao.CeoRepository;
import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.global.error.exception.CustomEntityNotFoundException;
import com.gokoy.delivery.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CeoServiceForAuth implements UserDetailsService {

    private final CeoRepository ceoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Ceo ceo = ceoRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException("email", ErrorCode.EMAIL_NOT_FOUND));

        return User.builder()
                .username(ceo.getUsername())
                .password(ceo.getPassword())
                .roles(ceo.getRole())
                .build();
    }

}
