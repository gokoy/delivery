package com.gokoy.delivery.domain.consumer.application;

import com.gokoy.delivery.domain.consumer.dao.ConsumerRepository;
import com.gokoy.delivery.domain.consumer.domain.Consumer;
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
public class ConsumerServiceForAuth implements UserDetailsService {

    private final ConsumerRepository consumerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Consumer consumer = consumerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException("email", ErrorCode.EMAIL_NOT_FOUND));

        return User.builder()
                .username(consumer.getUsername())
                .password(consumer.getPassword())
                .roles(consumer.getRole())
                .build();
    }

}
