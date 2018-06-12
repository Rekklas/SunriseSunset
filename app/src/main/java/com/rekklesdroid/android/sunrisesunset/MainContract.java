package com.rekklesdroid.android.sunrisesunset;

import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.rekklesdroid.android.sunrisesunset.entity.Results;
import com.rekklesdroid.android.sunrisesunset.service.ApiService;

public interface MainContract {
    interface View {
        void showLoading();

        void hideLoading();

        void publishData(Results data);

        void showInfoMessage(String msg);
    }

    interface Presenter {
        // User actions
        void onGetInfoBtnClicked();

        void onPlaceSelected(Place place);

        // Model updates
        void onDestroy();
    }

    interface Interactor {
        void loadResults(ApiService apiService);
        void loadResults(ApiService apiService, Place place);

    }

    interface InteractorOutput {
        void onQuerySuccess(Results data);

        void onQueryError();
    }
}
