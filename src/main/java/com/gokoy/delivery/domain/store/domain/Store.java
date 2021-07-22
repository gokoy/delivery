package com.gokoy.delivery.domain.store.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.gokoy.delivery.domain.menu.domain.Menu;
import com.gokoy.delivery.domain.menugroup.domain.MenuGroup;
import com.gokoy.delivery.domain.model.Address;
import com.gokoy.delivery.domain.model.BaseTimeEntity;
import com.gokoy.delivery.domain.model.Money;
import com.gokoy.delivery.domain.option.domain.Option;
import com.gokoy.delivery.domain.optiongroup.domain.MenuOption;
import com.gokoy.delivery.domain.optiongroup.domain.OptionGroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "store_id")
	private Long id;

	private String name;

	private String phone;

	@Embedded
	private Address address;

	private String introduction;

	@Embedded
	private OperatingTime operatingTime;

	private Money minimumOrderPrice;

	@OneToMany(mappedBy = "store")
	private List<MenuGroup> menuGroups;

	@OneToMany(mappedBy = "store")
	private List<Menu> menus;

	@OneToMany(mappedBy = "store")
	private List<OptionGroup> optionGroups;

	@OneToMany(mappedBy = "store")
	private List<Option> options;

	@OneToMany(mappedBy = "store")
	private List<MenuOption> menuOptions;
}
