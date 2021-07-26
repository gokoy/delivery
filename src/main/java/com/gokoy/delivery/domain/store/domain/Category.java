package com.gokoy.delivery.domain.store.domain;

public enum Category {

	KOREAN(1, "한식"),
	SCHOOL_FOOD(2, "분식"),
	DESSERT(3, "디저트"),
	JAPANESE(4, "일식"),
	CHICKEN(5, "치킨"),
	PIZZA(6, "피자"),
	ASIAN_WESTERN(7, "아시안,양식"),
	CHINESE(8, "중식"),
	JOKBAL_BOSSAM(9, "족발,보쌈"),
	MIDNINGHT_MEAL(10, "야식"),
	STEAM_SOUP(11, "찜,탕"),
	LUNCH_BOX(12, "도시락"),
	FAST_FOOD(13, "패스트푸드"),
	VEGETARIAN(14, "채식");

	private Integer id;

	private String type;

	Category(Integer id, String type) {
		this.id = id;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}
}
