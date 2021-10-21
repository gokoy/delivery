package com.gokoy.delivery.domain.ceo.domain;

import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.global.common.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "ceo_id")))

public class Ceo extends User {

    @OneToMany(mappedBy = "ceo")
    private List<Store> stores = new ArrayList<>();

    public Ceo(String email, String password, String phone) {
        super(email, password, Role.CEO.name(), phone);
    }

    public void addStore(Store store) {
        this.stores.add(store);
    }
}
