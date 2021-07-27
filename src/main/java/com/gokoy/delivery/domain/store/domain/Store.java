package com.gokoy.delivery.domain.store.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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

	private String introduction;

	@Embedded
	private Address address;

	@Embedded
	private OperatingTime operatingTime;

	private Money minimumOrderPrice;

	@ElementCollection
	@CollectionTable(name = "store_category", joinColumns = {
		@JoinColumn(name = "store_id", referencedColumnName = "store_id")})
	@Enumerated(EnumType.STRING)
	private Set<Category> categories = new HashSet<>();

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
}
