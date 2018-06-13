package com.rekklesdroid.android.sunrisesunset.interactor;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rekklesdroid.android.sunrisesunset.MainContract;
import com.rekklesdroid.android.sunrisesunset.entity.JsonData;
import com.rekklesdroid.android.sunrisesunset.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainInteractor implements MainContract.Interactor {

    private MainContract.InteractorOutput mInteractorOutput;

    private double mLatitude;
    private double mLongitude;

    public MainInteractor(MainContract.InteractorOutput interactorOutput) {
        mInteractorOutput = interactorOutput;
    }

    /**
     * Method loads JSON results from Sunrise/Sunset API
     *
     * @param apiService object that provides RetrofitClient for HTTP requests
     */
    @Override
    public void loadResults(ApiService apiService) {
        apiService.getApi().getTimesInfo(mLatitude, mLongitude)
                .enqueue(new Callback<JsonData>() {
                    @Override
                    public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                        JsonData data = response.body();

                        if (data != null && data.getResults() != null) {
                            mInteractorOutput.onQuerySuccess(data.getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonData> call, Throwable t) {
                        mInteractorOutput.onQueryError();
                    }
                });
    }

    /**
     * Method defines current device location and invokes
     * {@link #loadResults(ApiService)} for getting JSON response
     *
     * @param apiService object that provides RetrofitClient for HTTP requests
     * @param fusedLocationProviderClient using for getting current device location
     */
    @Override
    public void loadResults(final ApiService apiService,
                            FusedLocationProviderClient fusedLocationProviderClient) {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLatitude = location.getLatitude();
                                mLongitude = location.getLongitude();

                                loadResults(apiService);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mInteractorOutput.onGetLastLocationFailure();
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method defines location of selected city and invokes
     * {@link #loadResults(ApiService)} for getting JSON response
     *
     * @param apiService object that provides RetrofitClient for HTTP requests
     * @param place object which contains information about selected city
     */
    @Override
    public void loadResults(ApiService apiService, Place place) {
        mLatitude = place.getLatLng().latitude;
        mLongitude = place.getLatLng().longitude;
        loadResults(apiService);
    }
}
