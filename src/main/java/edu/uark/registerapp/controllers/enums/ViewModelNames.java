package edu.uark.registerapp.controllers.enums;

public enum ViewModelNames {
	NOT_DEFINED(""),
	ERROR_MESSAGE("errorMessage"),
	PLACE_HOLDER("placeholder"),
	IS_ELEVATED_USER("isElevatedUser"),
	EMPLOYEE_TYPES("employeeDetail"),
	PRODUCTS("products"), // Product listing
	PRODUCT("product"); // Product detail
	
	public String getValue() {
		return value;
	}
	
	private String value;

	private ViewModelNames(final String value) {
		this.value = value;
	}
}
