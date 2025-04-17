package com.example.caloriecraft.Interfaces;

import com.example.caloriecraft.Objects.NutritionResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NutritionixGetNutritionInfoApi {
    @Headers({
            "x-app-id: 4953a36a",
            "x-app-key: f39a6ac837b0cb3171951e6403fd04aa",
            "Content-Type: application/json"
    })
    @POST("v2/natural/nutrients")
    Call<NutritionResponse> getNutritionInfo(@Body RequestBody body);
}
