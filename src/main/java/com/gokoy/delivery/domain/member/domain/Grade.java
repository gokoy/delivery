package com.gokoy.delivery.domain.member.domain;

public enum Grade {

	THANKFUL(1, "고마운분"),
	PRECIOUS(2, "귀한분"),
	MORE_PRECIOUS(3, "더귀한분"),
	MATCH_MADE_HEAVEN(4, "천생연분");

	private Integer id;

	private String type;

	Grade(Integer id, String type) {
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
