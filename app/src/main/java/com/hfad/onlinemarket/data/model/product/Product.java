package com.hfad.onlinemarket.data.model.product;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("upsell_ids")
    private List<Object> upsellIds;

    @SerializedName("featured")
    private boolean featured;

    @SerializedName("purchasable")
    private boolean purchasable;

    @SerializedName("grouped_products")
    private List<Object> groupedProducts;

    @SerializedName("_links")
    private Links links;

    @SerializedName("tax_status")
    private String taxStatus;

    @SerializedName("catalog_visibility")
    private String catalogVisibility;

    @SerializedName("type")
    private String type;

    @SerializedName("external_url")
    private String externalUrl;

    @SerializedName("price")
    private String price;

    @SerializedName("meta_data")
    private List<Object> metaData;

    @SerializedName("id")
    private int id;

    @SerializedName("sku")
    private String sku;

    @SerializedName("slug")
    private String slug;

    @SerializedName("date_on_sale_from")
    private Object dateOnSaleFrom;

    @SerializedName("shipping_required")
    private boolean shippingRequired;

    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;

    @SerializedName("images")
    private List<ImagesItem> images;

    @SerializedName("stock_status")
    private String stockStatus;

    @SerializedName("price_html")
    private String priceHtml;

    @SerializedName("download_expiry")
    private int downloadExpiry;

    @SerializedName("backordered")
    private boolean backordered;

    @SerializedName("weight")
    private String weight;

    @SerializedName("rating_count")
    private int ratingCount;

    @SerializedName("tags")
    private List<TagsItem> tags;

    @SerializedName("date_on_sale_to")
    private Object dateOnSaleTo;

    @SerializedName("sold_individually")
    private boolean soldIndividually;

    @SerializedName("backorders")
    private String backorders;

    @SerializedName("shipping_taxable")
    private boolean shippingTaxable;

    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("download_limit")
    private int downloadLimit;

    @SerializedName("name")
    private String name;

    @SerializedName("shipping_class")
    private String shippingClass;

    @SerializedName("button_text")
    private String buttonText;

    @SerializedName("permalink")
    private String permalink;

    @SerializedName("status")
    private String status;

    @SerializedName("cross_sell_ids")
    private List<Object> crossSellIds;

    @SerializedName("short_description")
    private String shortDescription;

    @SerializedName("virtual")
    private boolean virtual;

    @SerializedName("downloadable")
    private boolean downloadable;

    @SerializedName("menu_order")
    private int menuOrder;

    @SerializedName("description")
    private String description;

    @SerializedName("date_on_sale_to_gmt")
    private Object dateOnSaleToGmt;

    @SerializedName("date_on_sale_from_gmt")
    private Object dateOnSaleFromGmt;

    @SerializedName("regular_price")
    private String regularPrice;

    @SerializedName("backorders_allowed")
    private boolean backordersAllowed;

    @SerializedName("downloads")
    private List<Object> downloads;

    @SerializedName("reviews_allowed")
    private boolean reviewsAllowed;

    @SerializedName("variations")
    private List<Object> variations;

    @SerializedName("categories")
    private List<CategoriesItem> categories;

    @SerializedName("total_sales")
    private int totalSales;

    @SerializedName("on_sale")
    private boolean onSale;

    @SerializedName("manage_stock")
    private boolean manageStock;

    @SerializedName("default_attributes")
    private List<Object> defaultAttributes;

    @SerializedName("purchase_note")
    private String purchaseNote;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("tax_class")
    private String taxClass;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("average_rating")
    private String averageRating;

    @SerializedName("stock_quantity")
    private Object stockQuantity;

    @SerializedName("sale_price")
    private String salePrice;

    @SerializedName("shipping_class_id")
    private int shippingClassId;

    @SerializedName("date_modified")
    private String dateModified;

    @SerializedName("related_ids")
    private List<Integer> relatedIds;

    @SerializedName("attributes")
    private List<Object> attributes;

    @SerializedName("dimensions")
    private Dimensions dimensions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImagesItem> getImages() {
        return images;
    }

    public String getPrice() {
        return priceFormatter(price)+" تومان";
    }

    public String getFeaturedImageUrl() {
        return getImages().get(0).getSrc();
    }

    public int getId() {
        return id;
    }

    public String getRegularPrice() {
        return priceFormatter(regularPrice);
    }

    public boolean isOnSale() {
        return onSale;
    }

    private String priceFormatter(String price) {
        String result = "";
        while (price.length() > 3) {
            result = ","+price.substring(price.length()-3).concat(result);
            price = price.substring(0, price.length()-3);
        }
        result = price.concat(result);
        return result;
    }
}

