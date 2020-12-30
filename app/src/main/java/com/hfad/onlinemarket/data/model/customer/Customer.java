package com.hfad.onlinemarket.data.model.customer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Customer{

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("role")
	private String role;

	@SerializedName("_links")
	private Links links;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("shipping")
	private Shipping shipping;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("meta_data")
	private List<MetaDataItem> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("is_paying_customer")
	private boolean isPayingCustomer;

	@SerializedName("username")
	private String username;

	public String getLastName() {
		return lastName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}