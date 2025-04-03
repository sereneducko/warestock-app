package com.example.stockapp;

class ModelRCInventoryItem {
    String imageUrl;
    String productName;
    String productCategory;
    String quantity;
    String barcode;
    String productPrice;

    public ModelRCInventoryItem(String imageUrl, String productName, String productCategory, String quantity, String barcode, String productPrice) {
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

    public String getQuantity() {
        return quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getProductPrice() {
        return productPrice;
    }
}
