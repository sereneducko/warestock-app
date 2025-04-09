package com.example.stockapp;

import java.util.List;

public class PaginationResponse {

    public List<ProductResponse> data;
    public LinkResponse links;
    public MetaResponse meta;
    public CategoryResponse categories;

    public List<ProductResponse> getData() {
        return data;
    }

    public LinkResponse getLinks() {
        return links;
    }

    public MetaResponse getMeta() {
        return meta;
    }

    public CategoryResponse getCategories() {return categories;}
}
