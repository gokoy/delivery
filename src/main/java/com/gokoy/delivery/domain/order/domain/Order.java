package com.gokoy.delivery.domain.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.global.common.model.Address;
import com.gokoy.delivery.global.common.model.BaseTimeEntity;
import com.gokoy.delivery.global.common.model.Money;
import com.gokoy.delivery.domain.store.domain.Store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "orders_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member orderer;

	@OneToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "order_foods")
	private List<OrderFood> orderFoods = new ArrayList<>();

	@Embedded
	private Address address;

	@Embedded
	private Money totalPrice;

	private LocalDateTime orderTime;
}