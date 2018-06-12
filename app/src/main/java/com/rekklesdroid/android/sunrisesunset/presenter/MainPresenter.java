package com.rekklesdroid.android.sunrisesunset.presenter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.rekklesdroid.android.sunrisesunset.MainContract;
import com.rekklesdroid.android.sunrisesunset.entity.Results;
import com.rekklesdroid.android.sunrisesunset.interactor.MainInteractor;
import com.rekklesdroid.android.sunrisesunset.service.ApiService;

public class MainPresenter implements MainContract.Presenter, MainContract.InteractorOutput {

    private MainContract.View mView;
    private MainContract.Interactor mInteractor;
    private ApiService mApiService;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mInteractor = new MainInteractor(this);

        if (mApiService == null) {
            mApiService = new ApiService();
        }
    }

    @Override
    public void onGetInfoForCurrentLocation() {
        mView.showLoading();
        mView.requestLocationPermission();
    }

    @Override
    public void onPlaceSelected(Place place) {
        mView.showLoading();
        mInteractor.loadResults(mApiService, place);
    }

    @Override
    public void onLocationPermissionGranted() {
        mFusedLocationProviderClient = mView.getFusedLocationProviderClient();
        mInteractor.loadResults(mApiService, mFusedLocationProviderClient);
    }

    @Override
    public void onLocationPermissionDenied() {
        mView.showInfoMessage("Location permission denied");
    }

    @Override
    public void onDestroy() {
        mView = null;
        mInteractor = null;
    }

    @Override
    public void onQuerySuccess(Results data) {
        mView.hideLoading();
        mView.publishData(data);
    }

    @Override
    public void onQueryError() {
        mView.hideLoading();
        mView.showInfoMessage("Error when loading data");
    }

    @Override
    public void onGetLastLocationFailure() {
        mView.hideLoading();
        mView.showInfoMessage("Error with getting current location");
    }
}
