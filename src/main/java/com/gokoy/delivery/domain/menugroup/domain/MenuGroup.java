package com.gokoy.delivery.domain.menugroup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.gokoy.delivery.domain.menu.domain.Menu;
import com.gokoy.delivery.domain.store.domain.Store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuGroup {
	@Id
	@GeneratedValue
	@Column(name = "menu_group_id")
	private Long id;

	private String name;

	private String introduction;

	private Long parentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@OneToMany(mappedBy = "menuGroup")
	private List<Menu> menus = new ArrayList<>();
}
