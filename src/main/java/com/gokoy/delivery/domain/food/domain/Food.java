package com.gokoy.delivery.domain.food.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.gokoy.delivery.domain.model.BaseTimeEntity;
import com.gokoy.delivery.domain.model.Money;
import com.gokoy.delivery.domain.store.domain.Store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "food_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	private String name;

	@Embedded
	private Money price;

	@Lob
	private String introduction;

}
