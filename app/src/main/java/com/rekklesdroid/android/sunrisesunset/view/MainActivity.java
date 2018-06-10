package com.rekklesdroid.android.sunrisesunset.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rekklesdroid.android.sunrisesunset.MainContract;
import com.rekklesdroid.android.sunrisesunset.R;
import com.rekklesdroid.android.sunrisesunset.entity.Results;
import com.rekklesdroid.android.sunrisesunset.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    MainContract.Presenter mPresenter;

    @BindView(R.id.btn_get_info)
    Button mBtnGetInfo;

    @BindView(R.id.tv_sunrise)
    TextView mTvSunrise;

    @BindView(R.id.tv_sunset)
    TextView mTvSunset;

    @BindView(R.id.prog_bar_loading_data)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void publishData(Results data) {
        Log.d("Sunrise:", data.getSunrise());
        mTvSunrise.setText(String.format("Sunrise - %s", data.getSunrise()));
        mTvSunset.setText(String.format("Sunset - %s", data.getSunset()));
    }

    @Override
    public void showInfoMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_get_info)
    public void onBtnGetInfoClick() {
        Log.d("btn", "clicked");
        mPresenter.onGetInfoBtnClicked();
    }
}
