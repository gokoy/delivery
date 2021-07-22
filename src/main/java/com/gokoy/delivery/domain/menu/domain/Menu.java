package com.gokoy.delivery.domain.menu.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.gokoy.delivery.domain.menugroup.domain.MenuGroup;
import com.gokoy.delivery.domain.model.Money;
import com.gokoy.delivery.domain.optiongroup.domain.MenuOption;
import com.gokoy.delivery.domain.store.domain.Store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
	@Id
	@GeneratedValue
	@Column(name = "menu_id")
	private Long id;

	private String name;

	private Money price;

	private boolean soldOut;

	private boolean hide;

	private Long parentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_group_id")
	private MenuGroup menuGroup;

	@OneToMany(mappedBy = "menu")
	private List<MenuOption> menuOptions;
}
