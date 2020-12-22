package com.hfad.onlinemarket.data.model.product;

import com.google.gson.annotations.SerializedName;

public class TagsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;
}