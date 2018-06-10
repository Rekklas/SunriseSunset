package com.rekklesdroid.android.sunrisesunset.presenter;

import com.rekklesdroid.android.sunrisesunset.MainContract;
import com.rekklesdroid.android.sunrisesunset.entity.Results;
import com.rekklesdroid.android.sunrisesunset.interactor.MainInteractor;
import com.rekklesdroid.android.sunrisesunset.service.ApiService;

public class MainPresenter implements MainContract.Presenter, MainContract.InteractorOutput {

    private MainContract.View mView;
    private MainContract.Interactor mInteractor;
    private ApiService mApiService;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mInteractor = new MainInteractor(this);

        if (mApiService == null) {
            mApiService = new ApiService();
        }
    }

    @Override
    public void onGetInfoBtnClicked() {
        mView.showLoading();
        mInteractor.loadResults(mApiService);
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
}
