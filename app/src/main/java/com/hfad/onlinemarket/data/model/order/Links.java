package com.hfad.onlinemarket.data.model.order;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("self")
	private List<SelfItem> self;

	@SerializedName("collection")
	private List<CollectionItem> collection;

	@SerializedName("customer")
	private List<CustomerItem> customer;
}