package co.jestrada.cupoescolarapp.attendant.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.constant.Maps;
import co.jestrada.cupoescolarapp.attendant.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.attendant.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.attendant.presenter.CurrentPositionMapPresenter;

public class CurrentPositionMapActivity extends FragmentActivity implements
        ICurrentPositionMapContract.ICurrentPositionMapView,
        OnMapReadyCallback {

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.btn_set_current_position)
    Button btnSetCurrentPosition;

    private List<Address> mAddress;
    private Marker mMarker;

    private GoogleMap mMap;

    private CurrentPositionMapPresenter mCurrentPositionMapPresenter;
    private RefPositionBO refPositionBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_position_map);

        mCurrentPositionMapPresenter = new CurrentPositionMapPresenter(this);

        ButterKnife.bind(this);
        pb.setVisibility(View.VISIBLE);

        try {
            int gpsSignal = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        refPositionBO = new RefPositionBO();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @OnClick(R.id.btn_set_current_position)
    public void saveCurrentPosition() {
        pb.setVisibility(View.VISIBLE);
        mCurrentPositionMapPresenter.saveRefPosition(refPositionBO);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        pb.setVisibility(View.GONE);

        mMap = googleMap;
        mMap.setMinZoomPreference(Maps.MAP_ZOOM_MIN);
        mMap.setMaxZoomPreference(Maps.MAP_ZOOM_MAX);

        mMap.setContentDescription("Probando");

/*        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);*/


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                    double lat = marker.getPosition().latitude;
                    double lng = marker.getPosition().longitude;
                    setCurrentPositionMap(lat, lng);
            }
        });
    }

    private void getGeolocation(double lat, double lng){
        try {
            Geocoder mGeocoder;
            mGeocoder = new Geocoder(this, Locale.getDefault());
            mAddress = mGeocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRefPoint() {
        refPositionBO.setLat(mAddress.get(0).getLatitude());
        refPositionBO.setLng(mAddress.get(0).getLongitude());
        refPositionBO.setAddress(mAddress.get(0).getAddressLine(0));
        refPositionBO.setCity(mAddress.get(0).getLocality());
        refPositionBO.setAdminArea(mAddress.get(0).getAdminArea());
        refPositionBO.setPostalCode(mAddress.get(0).getPostalCode());
    }


    @Override
    public void setRefPosition(RefPositionBO refPositionBO) {
        pb.setVisibility(View.GONE);
        goToRefPosition();
    }

    @Override
    public void setCurrentPositionMap(double lat, double lng) {
        getGeolocation(lat, lng);
        setRefPoint();
        setMarkerMap(lat, lng);
        setCameraMap(lat, lng);
    }

    private void setCameraMap(double lat, double lng) {
        LatLng currentPosition = new LatLng(lat, lng);
        CameraPosition mCameraPosition = new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(Maps.MAP_ZOOM)
                .tilt(Maps.MAP_TILT)
                .bearing(Maps.MAP_BEARING)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
    }

    private void setMarkerMap(double lat, double lng) {
        LatLng currentPosition = new LatLng(lat, lng);
        if (mMarker == null){
            MarkerOptions mMarkerOptions = new MarkerOptions();
            mMarkerOptions
                    .position(currentPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle))
                    .draggable(true)
                    .title(getString(R.string.current_position))
                    .snippet(refPositionBO.getAddress());
            mMarker = mMap.addMarker(mMarkerOptions);
        }else{
            mMarker.setPosition(currentPosition);
            mMarker.setSnippet(refPositionBO.getAddress());
        }
    }

    private void goToRefPosition() {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrentPositionMapPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCurrentPositionMapPresenter.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCurrentPositionMapPresenter.onDestroy();
    }
}
