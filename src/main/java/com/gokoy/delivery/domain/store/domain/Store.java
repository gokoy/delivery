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
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.gokoy.delivery.domain.menu.domain.Menu;
import com.gokoy.delivery.domain.menugroup.domain.MenuGroup;
import com.gokoy.delivery.domain.menuoption.domain.MenuOption;
import com.gokoy.delivery.domain.option.domain.Option;
import com.gokoy.delivery.domain.optiongroup.domain.OptionGroup;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.BaseTimeEntity;
import com.gokoy.delivery.global.common.model.Money;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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
	@NotNull
	private Address address;

	@Embedded
	@NotNull
	private OperatingTime operatingTime;

	@Embedded
	@NotNull
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

	@Builder
	public Store(String name, String phone, String introduction, Address address,
		OperatingTime operatingTime, Money minimumOrderPrice,
		Set<Category> categories) {

		Assert.hasText(name, "name must have text");
		Assert.hasText(phone, "phone must have text");
		Assert.notNull(address, "address must not be null");
		Assert.notNull(operatingTime, "operatingTime must not be null");
		Assert.notNull(minimumOrderPrice, "minimumOrderPrice must not be null");
		Assert.notEmpty(categories, "categories must not be empty");

		this.name = name;
		this.phone = phone;
		this.introduction = introduction;
		this.address = address;
		this.operatingTime = operatingTime;
		this.minimumOrderPrice = minimumOrderPrice;
		this.categories = categories;
	}
}
