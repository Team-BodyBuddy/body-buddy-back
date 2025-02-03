package BodyBuddy.demo.global.common.commonEnum;

public enum ItemStatus {
	ACTIVE("구매함"),
	INACTIVE("구매하지 않음"),
	COMING_SOON("출시 예정");

	private final String description;

	ItemStatus(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}