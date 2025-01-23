package BodyBuddy.demo.global.common.commonEnum;

public enum ItemStatus {
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	COMING_SOON("COMING_SOON");

	private final String description;

	ItemStatus(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
}