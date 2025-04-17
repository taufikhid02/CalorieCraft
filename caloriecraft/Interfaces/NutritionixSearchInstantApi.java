package com.example.caloriecraft.Interfaces;

import com.example.caloriecraft.Objects.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NutritionixSearchInstantApi {
    @Headers({
            "x-app-id: 4953a36a",
            "x-app-key: f39a6ac837b0cb3171951e6403fd04aa",
            "Content-Type: application/json"
    })
    @GET("v2/search/instant")
    Call<SearchResponse> searchInstant(@Query("query") String query);
}