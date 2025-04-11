package com.example.stockapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchPaginationResponse {

    @SerializedName("data")
    public List<BatchResponse> data;
    public LinkResponse links;
    public MetaResponse meta;
}
