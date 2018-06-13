package com.rekklesdroid.android.sunrisesunset.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.rekklesdroid.android.sunrisesunset.MainContract;
import com.rekklesdroid.android.sunrisesunset.R;
import com.rekklesdroid.android.sunrisesunset.entity.Results;
import com.rekklesdroid.android.sunrisesunset.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View, PlaceSelectionListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    MainContract.Presenter mPresenter;

    @BindView(R.id.btn_get_info)
    Button mBtnGetInfoForCurrentLocation;

    @BindView(R.id.tv_sunrise_sunset)
    TextView mTvSunriseSunset;

    @BindView(R.id.prog_bar_loading_data)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this);

        initPlaceAutocompleteFragment();
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
        mTvSunriseSunset.setText(getString(R.string.sunrise_sunset_text,
                data.getSunrise(), data.getSunset(),
                data.getSolarNoon(), data.getDayLength(),
                data.getCivilTwilightBegin(), data.getCivilTwilightEnd(),
                data.getNauticalTwilightBegin(), data.getNauticalTwilightEnd(),
                data.getAstronomicalTwilightBegin(), data.getAstronomicalTwilightEnd()));
    }

    @Override
    public void showInfoMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mPresenter.onLocationPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.onLocationPermissionGranted();
                } else {
                    mPresenter.onLocationPermissionDenied();
                }
                break;
        }
    }

    @OnClick(R.id.btn_get_info)
    public void onGetInfoForCurrentLocationClicked() {
        mPresenter.onGetInfoForCurrentLocation();
    }

    /**
     * invokes when city from search view was selected
     *
     * @param place contains selected city's information
     */
    @Override
    public void onPlaceSelected(Place place) {
        mPresenter.onPlaceSelected(place);
    }

    /**
     * invokes if selection of city was failed
     *
     * @param status status of selection
     */
    @Override
    public void onError(Status status) {
        Toast.makeText(this,
                getString(R.string.failure_place_selection, status.getStatusMessage()),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * initialize autocompleteFragment with search view for places selection
     */
    private void initPlaceAutocompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.fragment_autocomplete);
        autocompleteFragment.setHint(getString(R.string.hint_search_for_any_city));

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }
}
