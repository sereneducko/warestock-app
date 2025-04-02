package com.example.stockapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockAppService {
    @GET("products")
    Call<PaginationResponse> getItems(@Query("page") int page, @Query("per_page") int perPage);
}
