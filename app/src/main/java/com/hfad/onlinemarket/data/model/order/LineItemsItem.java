package com.hfad.onlinemarket.data.model.order;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LineItemsItem{

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("tax_class")
	private String taxClass;

	@SerializedName("taxes")
	private List<Object> taxes;

	@SerializedName("total_tax")
	private String totalTax;

	@SerializedName("total")
	private String total;

	@SerializedName("variation_id")
	private int variationId;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("subtotal_tax")
	private String subtotalTax;

	@SerializedName("sku")
	private String sku;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}