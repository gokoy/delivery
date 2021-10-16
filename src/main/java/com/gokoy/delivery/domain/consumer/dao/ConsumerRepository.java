package com.gokoy.delivery.domain.consumer.dao;

import com.gokoy.delivery.domain.consumer.domain.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByEmail(String email);

}
