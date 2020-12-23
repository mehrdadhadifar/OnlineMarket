package com.hfad.onlinemarket.data.model.product;

import com.google.gson.annotations.SerializedName;

public class Image{

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("src")
	private String src;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("name")
	private String name;

	@SerializedName("alt")
	private String alt;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("id")
	private int id;

	public String getSrc() {
		return src;
	}
}