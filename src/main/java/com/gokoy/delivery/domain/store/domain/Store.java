package com.gokoy.delivery.domain.store.domain;

import com.gokoy.delivery.domain.category.Category;
import com.gokoy.delivery.domain.menu.domain.Menu;
import com.gokoy.delivery.domain.menugroup.domain.MenuGroup;
import com.gokoy.delivery.domain.menuoption.domain.MenuOption;
import com.gokoy.delivery.domain.option.domain.Option;
import com.gokoy.delivery.domain.optiongroup.domain.OptionGroup;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.BaseTimeEntity;
import com.gokoy.delivery.global.common.model.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
            @AttributeOverride(name = "name", column = @Column(name = "address_name")),
            @AttributeOverride(name = "detail", column = @Column(name = "address_detail")),
    })
    @NotNull
    private Address address;

    @Embedded
    @NotNull
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
    @NotNull
    private Money deliveryTip;

    //연관관계

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

    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();
}
