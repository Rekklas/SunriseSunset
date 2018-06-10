package com.rekklesdroid.android.sunrisesunset.interactor;

import android.util.Log;

import com.rekklesdroid.android.sunrisesunset.MainContract;
import com.rekklesdroid.android.sunrisesunset.entity.JsonData;
import com.rekklesdroid.android.sunrisesunset.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainInteractor implements MainContract.Interactor {

    private MainContract.InteractorOutput mInteractorOutput;

    public MainInteractor(MainContract.InteractorOutput interactorOutput) {
        mInteractorOutput = interactorOutput;
    }


    @Override
    public void loadResults(ApiService apiService) {
        double latitude = 36.7201600;
        double longitude = -4.4203400;
        apiService.getApi().getTimesInfo(latitude, longitude)
                .enqueue(new Callback<JsonData>() {
                    @Override
                    public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                        JsonData data = response.body();
                        Log.d("response", data.getResults().getSunrise());

                        if (data != null && data.getResults() != null) {

                            mInteractorOutput.onQuerySuccess(data.getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonData> call, Throwable t) {
                        Log.d("failure", "failure");
                        mInteractorOutput.onQueryError();
                    }
                });
    }
}
