package com.example.stockapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface StockAppService {
    @GET("products")
    Call<ProductPaginationResponse> getItems(@Query("page") int page, @Query("per_page") int perPage);
    @GET
    Call<ProductPaginationResponse> getItemsByUrl(@Url String url);

    // TODO: 07/04/2025 add a store api

    @POST("products")
    Call<ProductResponse> postProduct (@Body ProductRequest productRequest);


    //categories api
    @GET("categories")
    Call<CategoryListResponse> getCategories();

    @FormUrlEncoded
    @POST("categories")
    Call<WrapperCategoryResponse> postCategory (@Field("name") String name);

    //ProductBatch API
    @GET("batches")
    Call<BatchPaginationResponse>
}