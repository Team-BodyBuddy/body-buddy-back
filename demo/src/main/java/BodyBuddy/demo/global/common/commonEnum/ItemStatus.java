package BodyBuddy.demo.global.common.commonEnum;

public enum ItemStatus {
	ACTIVE("활성화"),
	INACTIVE("비활성화"),
	COMING_SOON("출시 예정");

	private final String description;

	ItemStatus(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}