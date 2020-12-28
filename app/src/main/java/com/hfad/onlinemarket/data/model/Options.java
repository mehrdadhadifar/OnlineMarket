package com.hfad.onlinemarket.data.model;

import java.io.Serializable;

public class Options implements Serializable {
    private OrderBy orderBy = OrderBy.date;
    private String categoryId = "";
    private String searchQuery = "";
    private String order = "desc";


    public Options getAcsOrder() {
        Options ascOptions=new Options(orderBy,categoryId,searchQuery);
        ascOptions.setOrder("asc");
        return ascOptions;
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
}
