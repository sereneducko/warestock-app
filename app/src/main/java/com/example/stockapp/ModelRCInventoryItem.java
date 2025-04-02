package com.example.stockapp;

class ModelRCInventoryItem {
    String imageUrl;
    String productName;
    String productCategory;
    int quantity;
    String barcode;
    long productPrice;

    public ModelRCInventoryItem(String imageUrl, String productName, String productCategory, int quantity, String barcode, long productPrice) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productCategory = productCategory;
        this.quantity = quantity;
        this.barcode = barcode;
        this.productPrice = productPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public long getProductPrice() {
        return productPrice;
    }
}
