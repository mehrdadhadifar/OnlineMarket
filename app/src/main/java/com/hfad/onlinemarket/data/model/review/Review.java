package com.hfad.onlinemarket.data.model.review;

import android.os.Build;
import android.text.Html;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("reviewer_avatar_urls")
    private ReviewerAvatarUrls reviewerAvatarUrls;

    @SerializedName("_links")
    private Links links;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("review")
    private String review;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("verified")
    private boolean verified;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("id")
    private int id;

    @SerializedName("reviewer")
    private String reviewer;

    @SerializedName("reviewer_email")
    private String reviewerEmail;

    @SerializedName("status")
    private String status;

    public String getDateCreated() {
        return dateCreated.substring(0, dateCreated.indexOf('T'));
    }

    public int getProductId() {
        return productId;
    }

    public int getId() {
        return id;
    }

    public String getReview() {
        if (review == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(review, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(review).toString();
        }
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRatingString() {
        return String.valueOf(rating);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    public Review(String review, int productId, int rating, String reviewer, String reviewerEmail) {
        this.review = review;
        this.productId = productId;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewerEmail = reviewerEmail;
    }
}