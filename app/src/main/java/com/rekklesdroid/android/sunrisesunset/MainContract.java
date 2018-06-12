package com.rekklesdroid.android.sunrisesunset;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.rekklesdroid.android.sunrisesunset.entity.Results;
import com.rekklesdroid.android.sunrisesunset.service.ApiService;

public interface MainContract {
    interface View {
        void showLoading();

        void hideLoading();

        void publishData(Results data);

        void showInfoMessage(String msg);

        FusedLocationProviderClient getFusedLocationProviderClient();

        void requestLocationPermission();
    }

    interface Presenter {
        // User actions
        void onGetInfoForCurrentLocation();

        void onPlaceSelected(Place place);

        void onLocationPermissionGranted();

        void onLocationPermissionDenied();

        // Model updates
        void onDestroy();
    }

    interface Interactor {
        void loadResults(ApiService apiService);

        void loadResults(ApiService apiService,
                         FusedLocationProviderClient fusedLocationProviderClient);

        void loadResults(ApiService apiService, Place place);

    }

    interface InteractorOutput {
        void onQuerySuccess(Results data);

        void onQueryError();

        void onGetLastLocationFailure();
    }
}
