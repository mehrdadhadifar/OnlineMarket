package com.hfad.onlinemarket.data.model;

import java.io.Serializable;

public class Options implements Serializable {
    private Order mOrder = Order.date;
    private String categoryId = "";
    private String searchQuery = "";

    public Options(Order order) {
        mOrder = order;
    }

    public Options(Order order, String searchQuery) {
        mOrder = order;
        this.searchQuery = searchQuery;
    }

    public Options(String categoryId, String searchQuery) {
        this.categoryId = categoryId;
        this.searchQuery = searchQuery;
    }

    public Options(Order order, int categoryId) {
        mOrder = order;
        this.categoryId = String.valueOf(categoryId);
    }

    public Options(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Options(int categoryId) {
        this.categoryId = String.valueOf(categoryId);
    }

    public Options(Order order, String categoryId, String searchQuery) {
        mOrder = order;
        this.categoryId = categoryId;
        this.searchQuery = searchQuery;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
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
}
