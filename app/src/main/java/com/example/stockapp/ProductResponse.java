package com.example.stockapp;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("name")
    private String name;
    private int quantity;
    private String sku;
    private String imageUrl;
    private String unit;
    private String categoryId;
    private String barcode;
    private String description;
    private int lowAmountThreshold;
    private long productPrice;
}
