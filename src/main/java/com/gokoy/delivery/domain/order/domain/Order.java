package com.gokoy.delivery.domain.order.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.gokoy.delivery.domain.member.domain.Member;
import com.gokoy.delivery.domain.model.Address;
import com.gokoy.delivery.domain.model.BaseTimeEntity;
import com.gokoy.delivery.domain.model.Money;
import com.gokoy.delivery.domain.store.domain.Store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member orderer;

	@OneToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@Embedded
	private List<OrderFood> orderFoods;

	@Embedded
	private Address address;

	@Embedded
	private Money totalPrice;
}
