package com.rekklesdroid.android.sunrisesunset.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static final String BASE_URL="https://api.sunrise-sunset.org/";

    private Retrofit mRetrofit = null;

    public ApiEndpoint getApi(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit.create(ApiEndpoint.class);
    }
}
