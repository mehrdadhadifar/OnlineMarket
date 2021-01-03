package com.hfad.onlinemarket.data.model.order;

import com.google.gson.annotations.SerializedName;

public class Shipping{

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

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
}