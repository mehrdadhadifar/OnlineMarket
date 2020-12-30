package com.hfad.onlinemarket.data.model.customer;

import com.google.gson.annotations.SerializedName;

public class Billing{

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("phone")
	private String phone;

	@SerializedName("address_1")
	private String address1;

	@SerializedName("address_2")
	private String address2;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("company")
	private String company;

	@SerializedName("state")
	private String state;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getPhone() {
		return phone;
	}
}