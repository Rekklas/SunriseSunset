package com.rekklesdroid.android.sunrisesunset;

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

        // Model updates
        void onDestroy();
    }

    interface Interactor {
        void loadResults(ApiService apiService);
    }

    interface InteractorOutput {
        void onQuerySuccess(Results data);

        void onQueryError();
    }
}
