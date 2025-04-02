package com.example.stockapp;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("name")
    private String name;
    private int quantity;
    private String sku;
    private String imageUrl;
    private String unit;

    private Category category;

    private String barcode;
    private String description;
    private int lowAmountThreshold;
    private long productPrice;
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSku() {
        return sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDescription() {
        return description;
    }

    public int getLowAmountThreshold() {
        return lowAmountThreshold;
    }

    public long getProductPrice() {
        return productPrice;
    }
}
