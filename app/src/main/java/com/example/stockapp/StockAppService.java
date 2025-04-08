package com.example.stockapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface StockAppService {
    @GET("products")
    Call<PaginationResponse> getItems(@Query("page") int page, @Query("per_page") int perPage);
    @GET
    Call<PaginationResponse> getItemsByUrl(@Url String url);

//    @POST("product")
//    Call<>

}