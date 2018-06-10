package com.rekklesdroid.android.sunrisesunset.service;

import com.rekklesdroid.android.sunrisesunset.entity.JsonData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoint {

    @GET("json")
    Call<JsonData> getTimesInfo(@Query("lat") double latitude,
                                @Query("lng") double longitude);
}
