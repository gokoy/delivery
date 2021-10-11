package com.gokoy.delivery.domain.store.dao;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.global.common.model.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void 카테고리로_store_찾기() {
        //given
        Store store1 = Store.builder()
                .name("store1")
                .minimumOrderPrice(new Money(10000))
                .phone("010-1234-5678")
                .build();

        store1
                .addCategory(new Category(StoreType.KOREAN))
                .addCategory(new Category(StoreType.JAPANESE));


        Store store2 = Store.builder()
                .name("store2")
                .minimumOrderPrice(new Money(10000))
                .phone("010-1234-5678")
                .build();

        store2
                .addCategory(new Category(StoreType.JAPANESE))
                .addCategory(new Category(StoreType.CHINESE));

        Store store3 = Store.builder()
                .name("store3")
                .minimumOrderPrice(new Money(10000))
                .phone("010-1234-5678")
                .build();

        store3
                .addCategory(new Category(StoreType.CHINESE))
                .addCategory(new Category(StoreType.KOREAN));

        storeRepository.save(store1);
        storeRepository.save(store2);
        storeRepository.save(store3);

        //when
        List<Store> foundStores = storeRepository.findByCategory(StoreType.KOREAN);

        //then
        assertThat(foundStores.size()).isEqualTo(2);
    }
}