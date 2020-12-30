package com.hfad.onlinemarket.data.model.customer;

import com.google.gson.annotations.SerializedName;

public class MetaDataItem{

	@SerializedName("id")
	private int id;

	@SerializedName("value")
	private String value;

	@SerializedName("key")
	private String key;
}