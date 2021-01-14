package com.hfad.onlinemarket.data.model.coupon;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Coupon{

	@SerializedName("usage_limit")
	private Object usageLimit;

	@SerializedName("code")
	private String code;

	@SerializedName("free_shipping")
	private boolean freeShipping;

	@SerializedName("_links")
	private Links links;

	@SerializedName("description")
	private String description;

	@SerializedName("minimum_amount")
	private String minimumAmount;

	@SerializedName("email_restrictions")
	private List<Object> emailRestrictions;

	@SerializedName("exclude_sale_items")
	private boolean excludeSaleItems;

	@SerializedName("excluded_product_ids")
	private List<Object> excludedProductIds;

	@SerializedName("usage_count")
	private int usageCount;

	@SerializedName("individual_use")
	private boolean individualUse;

	@SerializedName("usage_limit_per_user")
	private Object usageLimitPerUser;

	@SerializedName("limit_usage_to_x_items")
	private Object limitUsageToXItems;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("amount")
	private String amount;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("maximum_amount")
	private String maximumAmount;

	@SerializedName("discount_type")
	private String discountType;

	@SerializedName("used_by")
	private List<Object> usedBy;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("product_ids")
	private List<Object> productIds;

	@SerializedName("product_categories")
	private List<Object> productCategories;

	@SerializedName("date_expires")
	private String dateExpires;

	@SerializedName("excluded_product_categories")
	private List<Object> excludedProductCategories;

	@SerializedName("date_expires_gmt")
	private String dateExpiresGmt;

	public Object getUsageLimit(){
		return usageLimit;
	}

	public String getCode(){
		return code;
	}

	public boolean isFreeShipping(){
		return freeShipping;
	}

	public Links getLinks(){
		return links;
	}

	public String getDescription(){
		return description;
	}

	public String getMinimumAmount(){
		return minimumAmount;
	}

	public List<Object> getEmailRestrictions(){
		return emailRestrictions;
	}

	public boolean isExcludeSaleItems(){
		return excludeSaleItems;
	}

	public List<Object> getExcludedProductIds(){
		return excludedProductIds;
	}

	public int getUsageCount(){
		return usageCount;
	}

	public boolean isIndividualUse(){
		return individualUse;
	}

	public Object getUsageLimitPerUser(){
		return usageLimitPerUser;
	}

	public Object getLimitUsageToXItems(){
		return limitUsageToXItems;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public int getId(){
		return id;
	}

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public String getAmount(){
		return amount;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public String getMaximumAmount(){
		return maximumAmount;
	}

	public String getDiscountType(){
		return discountType;
	}

	public List<Object> getUsedBy(){
		return usedBy;
	}

	public String getDateModified(){
		return dateModified;
	}

	public List<Object> getProductIds(){
		return productIds;
	}

	public List<Object> getProductCategories(){
		return productCategories;
	}

	public String getDateExpires(){
		return dateExpires;
	}

	public List<Object> getExcludedProductCategories(){
		return excludedProductCategories;
	}

	public String getDateExpiresGmt(){
		return dateExpiresGmt;
	}
}