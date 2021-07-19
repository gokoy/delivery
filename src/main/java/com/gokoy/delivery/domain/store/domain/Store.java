package com.gokoy.delivery.domain.store.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.gokoy.delivery.domain.food.domain.Food;
import com.gokoy.delivery.domain.model.Address;
import com.gokoy.delivery.domain.model.BaseTimeEntity;
import com.gokoy.delivery.domain.model.Money;

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

	@Lob
	private String introduction;

	@Embedded
	private OperatingTime operatingTime;

	private Money minimumOrderPrice;

	@OneToMany(mappedBy = "store")
	private List<Food> foods = new ArrayList<>();

}
