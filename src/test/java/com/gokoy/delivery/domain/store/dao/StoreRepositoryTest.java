package com.gokoy.delivery.domain.store.dao;

import com.gokoy.delivery.domain.store.domain.Category;
import com.gokoy.delivery.domain.store.domain.Store;
import com.gokoy.delivery.domain.store.domain.StoreType;
import com.gokoy.delivery.global.common.model.Money;
import com.gokoy.delivery.global.config.querydsl.QueryDslConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // 내부에 @ExtendWith(SpringExtension.class) 포함
@Import(QueryDslConfig.class)
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    void set_data() {
        //given
        Store store1 = Store.builder()
                .name("store1")
                .phone("010-1234-5678")
                .minimumOrderPrice(new Money(1000))
                .deliveryTip(new Money(3000))
                .build()
                .addCategory(new Category(StoreType.KOREAN));
        storeRepository.save(store1);

        Store store2 = Store.builder()
                .name("store2")
                .phone("010-1234-5678")
                .minimumOrderPrice(new Money(5000))
                .deliveryTip(new Money(1000))
                .build()
                .addCategory(new Category(StoreType.KOREAN));
        storeRepository.save(store2);

        Store store3 = Store.builder()
                .name("store3")
                .phone("010-1234-5678")
                .minimumOrderPrice(new Money(10000))
                .deliveryTip(new Money(2000))
                .build()
                .addCategory(new Category(StoreType.CHINESE));
        storeRepository.save(store3);

        Store store4 = Store.builder()
                .name("store34")
                .phone("010-1234-5678")
                .minimumOrderPrice(new Money(12000))
                .deliveryTip(new Money(4000))
                .build()
                .addCategory(new Category(StoreType.CHINESE));
        storeRepository.save(store4);

        Store store5 = Store.builder()
                .name("store5")
                .phone("010-1234-5678")
                .minimumOrderPrice(new Money(13000))
                .deliveryTip(new Money(5000))
                .build()
                .addCategory(new Category(StoreType.CHINESE));
        storeRepository.save(store5);
    }

    @Test
    void 전체_Stores_찾기() {
        //when
        List<Store> stores = storeRepository.findByCategory(null);

        //then
        assertThat(stores.size()).isEqualTo(5);
    }

    @Test
    void Category로_Store_찾기() {
        //when
        List<Store> koreanStores = storeRepository.findByCategory(StoreType.KOREAN);
        List<Store> chineseStores = storeRepository.findByCategory(StoreType.CHINESE);

        //then
        assertThat(koreanStores.size()).isEqualTo(2);
        assertThat(chineseStores.size()).isEqualTo(3);
    }

    @Test
    void 특정_Category의_Store를_배달_금액으로_오름차순_정렬하여_찾기() {
        //when
        List<Store> stores = storeRepository.findByCategoryOrderByDeliveryTipAsc(StoreType.KOREAN);

        //then
        assertThat(stores.size()).isEqualTo(2);
        assertThat(stores.get(0).getDeliveryTip().getValue()).isEqualTo(1000);
        assertThat(stores.get(1).getDeliveryTip().getValue()).isEqualTo(3000);
    }

    @Test
    void 지정한_최소_주문_금액_범위_내_특정_Category의_Store_찾기() {
        //when
        List<Store> stores = storeRepository.findByCategoryBetweenMinimumOrderPrice(StoreType.CHINESE, 12000, 13000);

        //then
        assertThat(stores.size()).isEqualTo(2);
        assertThat(stores.get(0).getMinimumOrderPrice().getValue()).isEqualTo(12000);
        assertThat(stores.get(1).getMinimumOrderPrice().getValue()).isEqualTo(13000);
    }
}