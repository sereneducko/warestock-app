package com.example.stockapp;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse {

    @SerializedName("name")
    private String categoryName;

    @SerializedName("id")
    private int categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
