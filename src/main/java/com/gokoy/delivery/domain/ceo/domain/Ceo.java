package com.gokoy.delivery.domain.ceo.domain;

import com.gokoy.delivery.global.common.model.Role;
import com.gokoy.delivery.global.common.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "ceo_id")))

public class Ceo extends User {
    public Ceo(String email, String password, String phone) {
        super(email, password, Role.CEO.name(), phone);
    }
}
