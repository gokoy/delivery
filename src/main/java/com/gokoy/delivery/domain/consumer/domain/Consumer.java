package com.gokoy.delivery.domain.consumer.domain;

import com.gokoy.delivery.domain.order.domain.Order;
import com.gokoy.delivery.global.common.model.Address;
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
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "consumer_id")))
public class Consumer extends User {

    private String nickname;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "consumer_address", joinColumns = {
            @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id")
    })
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToMany(mappedBy = "orderer")
    List<Order> orders = new ArrayList<>();

    public Consumer(String email, String password, String phone) {
        super(email, password, "CONSUMER", phone);
        this.nickname = email.substring(0, email.indexOf('@'));
        this.grade = Grade.THANKFUL;
    }
}
