package com.example.stockapp;

public class ProductRequest {

    String name;
    String sku;
    String imageUrl;
    String unit;
    int category;
    String barcode;
    String description;
    int threshold;
    int price;

    public ProductRequest(String name, String sku, String imageUrl, String unit, int category, String barcode, String description, int threshold, int price) {
        this.name = name;
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.unit = unit;
        this.category = category;
        this.barcode = barcode;
        this.description = description;
        this.threshold = threshold;
        this.price = price;
    }
}
