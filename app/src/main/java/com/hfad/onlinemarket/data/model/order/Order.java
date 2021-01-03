package com.hfad.onlinemarket.data.model.order;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("discount_total")
	private String discountTotal;

	@SerializedName("order_key")
	private String orderKey;

	@SerializedName("prices_include_tax")
	private boolean pricesIncludeTax;

	@SerializedName("_links")
	private Links links;

	@SerializedName("customer_note")
	private String customerNote;

	@SerializedName("line_items")
	private List<LineItemsItem> lineItems;

	@SerializedName("coupon_lines")
	private List<Object> couponLines;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("refunds")
	private List<Object> refunds;

	@SerializedName("number")
	private String number;

	@SerializedName("total")
	private String total;

	@SerializedName("shipping")
	private Shipping shipping;

	@SerializedName("date_paid_gmt")
	private Object datePaidGmt;

	@SerializedName("tax_lines")
	private List<Object> taxLines;

	@SerializedName("date_paid")
	private Object datePaid;

	@SerializedName("customer_user_agent")
	private String customerUserAgent;

	@SerializedName("payment_method_title")
	private String paymentMethodTitle;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("date_completed")
	private Object dateCompleted;

	@SerializedName("currency")
	private String currency;

	@SerializedName("id")
	private int id;

	@SerializedName("date_completed_gmt")
	private Object dateCompletedGmt;

	@SerializedName("payment_method")
	private String paymentMethod;

	@SerializedName("shipping_tax")
	private String shippingTax;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("cart_hash")
	private String cartHash;

	@SerializedName("shipping_total")
	private String shippingTotal;

	@SerializedName("cart_tax")
	private String cartTax;

	@SerializedName("currency_symbol")
	private String currencySymbol;

	@SerializedName("created_via")
	private String createdVia;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("discount_tax")
	private String discountTax;

	@SerializedName("total_tax")
	private String totalTax;

	@SerializedName("version")
	private String version;

	@SerializedName("customer_ip_address")
	private String customerIpAddress;

	@SerializedName("shipping_lines")
	private List<Object> shippingLines;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("fee_lines")
	private List<Object> feeLines;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("status")
	private String status;

	public List<LineItemsItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItemsItem> lineItems) {
		this.lineItems = lineItems;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}