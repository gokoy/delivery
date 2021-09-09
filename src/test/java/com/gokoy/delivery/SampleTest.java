package com.gokoy.delivery;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.OperatingTime;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.Money;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.Set;

@DataJpaTest
public class SampleTest {
    @Autowired
    EntityManager em;

    @Test
    void valueQuery() {
        Store newStore = Store.builder()
                .name("name")
                .phone("010-1234-5678")
                .introduction("introduction")
                .address(new Address("addressName", "addressDetail"))
                .operatingTime(new OperatingTime(1, LocalTime.of(8, 0), LocalTime.of(18, 0)))
                .minimumOrderPrice(new Money(15000))
                .deliveryTip(new Money(3000))
                .categories(Set.of(Category.KOREAN))
                .build();

        em.persist(newStore);
        em.flush();
        em.clear();

        JPAQueryFactory query = new JPAQueryFactory(em);

//        List<Store> result = query.select(store)
//                .from(store)
//                .join(store.categories, )
//                .where()
//                .fetch();
    }
}
