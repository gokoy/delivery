package com.gokoy.delivery.domain.store.dao;

import com.gokoy.delivery.config.TestConfig;
import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.global.common.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 내부에 @ExtendWith(SpringExtension.class) 포함
@Import(TestConfig.class)
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void QueryDsl_카테고리로_store_찾기() {
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