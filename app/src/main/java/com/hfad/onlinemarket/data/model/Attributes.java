package com.hfad.onlinemarket.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Attributes {

	@SerializedName("visible")
	private boolean visible;

	@SerializedName("name")
	private String name;

	@SerializedName("options")
	private List<String> options;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private int position;

	@SerializedName("variation")
	private boolean variation;

	public String getName() {
		return name;
	}

	public List<String> getOptions() {
		return options;
	}
}