package com.gokoy.delivery.domain.store.domain;

import com.gokoy.delivery.domain.menu.domain.Menu;
import com.gokoy.delivery.domain.menugroup.domain.MenuGroup;
import com.gokoy.delivery.domain.menuoption.domain.MenuOption;
import com.gokoy.delivery.domain.option.domain.Option;
import com.gokoy.delivery.domain.optiongroup.domain.OptionGroup;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.BaseTimeEntity;
import com.gokoy.delivery.global.common.model.Money;
import lombok.*;
import org.springframework.util.Assert;

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

    @ElementCollection
    @CollectionTable(name = "store_category", joinColumns = {
            @JoinColumn(name = "store_id", referencedColumnName = "store_id")})
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();

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

    @Builder
    public Store(String name, String phone, String introduction, Address address,
                 OperatingTime operatingTime, Money minimumOrderPrice, Money deliveryTip,
                 Set<Category> categories) {

        Assert.hasText(name, "name must have text");
        Assert.hasText(phone, "phone must have text");
        Assert.notNull(address, "address must not be null");
        Assert.notNull(operatingTime, "operatingTime must not be null");
        Assert.notNull(minimumOrderPrice, "minimumOrderPrice must not be null");
        Assert.notNull(deliveryTip, "deliveryTip must not be null");
        Assert.notEmpty(categories, "categories must not be empty");

        this.name = name;
        this.phone = phone;
        this.introduction = introduction;
        this.address = address;
        this.operatingTime = operatingTime;
        this.minimumOrderPrice = minimumOrderPrice;
        this.deliveryTip = deliveryTip;
        this.categories = categories;
    }
}
