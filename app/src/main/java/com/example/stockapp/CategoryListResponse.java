package com.example.stockapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryListResponse {

    @SerializedName("data")
    public List<CategoryResponse> categories;

    public List<CategoryResponse> getCategories() {
        return categories;
    }
}
