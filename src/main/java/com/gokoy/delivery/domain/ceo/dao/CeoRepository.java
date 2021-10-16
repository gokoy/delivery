package com.gokoy.delivery.domain.ceo.dao;

import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.consumer.domain.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CeoRepository extends JpaRepository<Ceo, Long> {

    Optional<Ceo> findByEmail(String email);

}
