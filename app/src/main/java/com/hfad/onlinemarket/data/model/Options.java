package com.hfad.onlinemarket.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Options implements Serializable {
    private OrderBy orderBy = OrderBy.date;
    private String categoryId = "";
    private String searchQuery = "";
    private String order = "desc";
    private HashMap<String, String> tags = new HashMap<>();
    private boolean osSale;
    private boolean inStock;
    private String minPrice = null;
    private String maxPrice = null;
    private String filteredTagId = "";

    public Options() {
    }

    public String getFilteredTagId() {
        return filteredTagId;
    }

    public void setFilteredTagId(String filteredTagId) {
        this.filteredTagId = filteredTagId;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public List<String> getTagsId() {
        return new ArrayList<String>(tags.keySet());
    }


    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isOsSale() {
        return osSale;
    }

    public void setOsSale(boolean osSale) {
        this.osSale = osSale;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public List<String> getTagsName() {
        return new ArrayList<String>(tags.values());
    }


    public String getOrder() {
        return order;
    }

    private void setOrder(String order) {
        this.order = order;
    }

    public Options(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public Options(OrderBy orderBy, String searchQuery) {
        this.orderBy = orderBy;
        this.searchQuery = searchQuery;
    }

    public Options(String categoryId, String searchQuery) {
        this.categoryId = categoryId;
        this.searchQuery = searchQuery;
    }

    public Options(OrderBy orderBy, int categoryId) {
        this.orderBy = orderBy;
        this.categoryId = String.valueOf(categoryId);
    }

    public Options(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Options(int categoryId) {
        this.categoryId = String.valueOf(categoryId);
    }

    public Options(OrderBy orderBy, String categoryId, String searchQuery) {
        this.orderBy = orderBy;
        this.categoryId = categoryId;
        this.searchQuery = searchQuery;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setAscOrder() {
        order = "asc";
    }

    public void setDescOrder() {
        order = "desc";
    }

    @Override
    public String toString() {
        return "Options{" +
                "orderBy=" + orderBy +
                ", categoryId='" + categoryId + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                ", order='" + order + '\'' +
                ", tags=" + tags.keySet() +
                ", tags=" + tags.values() +
                ", osSale=" + osSale +
                ", inStock=" + inStock +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", filteredTagId='" + filteredTagId + '\'' +
                '}';
    }

}
