package com.gokoy.delivery.domain.store.domain;

public enum Category {

	KOREAN("한식"),
	SCHOOL_FOOD("분식"),
	DESSERT("디저트"),
	JAPANESE("일식"),
	CHICKEN("치킨"),
	PIZZA("피자"),
	ASIAN_WESTERN("아시안,양식"),
	CHINESE("중식"),
	JOKBAL_BOSSAM("족발,보쌈"),
	MIDNINGHT_MEAL("야식"),
	STEAM_SOUP("찜,탕"),
	LUNCH_BOX("도시락"),
	FAST_FOOD("패스트푸드"),
	VEGETARIAN("채식");

	private String type;

	Category(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.type;
	}
}
