package com.gokoy.delivery.domain.store.domain;

import com.gokoy.delivery.domain.ceo.domain.Ceo;
import com.gokoy.delivery.domain.menu.domain.Menu;
import com.gokoy.delivery.domain.menugroup.domain.MenuGroup;
import com.gokoy.delivery.domain.menuoption.domain.MenuOption;
import com.gokoy.delivery.domain.option.domain.Option;
import com.gokoy.delivery.domain.optiongroup.domain.OptionGroup;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.BaseTimeEntity;
import com.gokoy.delivery.global.common.model.Money;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String introduction;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "address")),
            @AttributeOverride(name = "detail", column = @Column(name = "address_detail")),
    })
    private Address address;

    @Embedded
    private OperatingTime operatingTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "minimum_order_price"))
    })
    @NotNull
    private Money minimumOrderPrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "delivery_tip"))
    })
    private Money deliveryTip;

    //연관관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ceo_id")
    private Ceo ceo;

    @OneToMany(mappedBy = "store")
    private List<MenuGroup> menuGroups = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<OptionGroup> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<MenuOption> menuOptions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "store_category", joinColumns = {
            @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    })
    private Set<Category> categories = new HashSet<>();

    @Builder
    public Store(String name, String phone, String introduction, Address address, OperatingTime operatingTime, Money minimumOrderPrice, Money deliveryTip) {
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(phone, "phone must not be empty");
        Assert.notNull(minimumOrderPrice, "minimumOrderPrice must not be empty");

        this.name = name;
        this.phone = phone;
        this.introduction = introduction;
        this.address = address;
        this.operatingTime = operatingTime;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
    }

    public Store addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public void setCeo(Ceo ceo) {
        this.ceo = ceo;
    }
}
