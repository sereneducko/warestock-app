package com.example.stockapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StockAppService {
    @GET("products")
    Call<List<ProductResponse>> getAllProducts ();
}
