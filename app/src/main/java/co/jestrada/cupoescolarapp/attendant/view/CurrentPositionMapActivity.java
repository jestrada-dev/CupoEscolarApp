package co.jestrada.cupoescolarapp.attendant.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

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

    LocationManager mLocationManager;
    private List<Address> mAddress;
    private Marker mMarker;

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationClient;

    private boolean locationEnableRequested = false;
    private boolean permissionAccessRequested = false;

    private static final int REQUEST_FINE_LOCATION = 1001;
    private static final int PERMISISION_FINE_LOCATION_GRANTED = 0;
    private static final int PERMISISION_FINE_LOCATION_DENIED = -1;

    private CurrentPositionMapPresenter mCurrentPositionMapPresenter;
    private RefPositionBO refPositionBO;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_position_map);

        mCurrentPositionMapPresenter = new CurrentPositionMapPresenter(this);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ButterKnife.bind(this);

        pb.setVisibility(View.VISIBLE);

        initGeolocationProcess();

/*        try {
            int gpsSignal = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }*/

        refPositionBO = new RefPositionBO();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void showRequestEnableLocation() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.access_location);
        mBuilder.setMessage(R.string.request_enable_location);
        mBuilder.setPositiveButton(R.string.go_to_granted_permission_location, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestEnableLocation();
            }
        });
        mBuilder.setNegativeButton(R.string.close_map, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToRefPosition();
            }
        });
        mBuilder.show();
    }

    private void requestEnableLocation() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        locationEnableRequested = true;
    }

    private void requestPermissionAccessLocation(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        permissionAccessRequested = true;
    }

    private boolean isGrantedPermissionAccessLocation() {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    @OnClick(R.id.btn_set_current_position)
    public void saveCurrentPosition() {
        pb.setVisibility(View.VISIBLE);
        mCurrentPositionMapPresenter.saveRefPositionNoDescription(refPositionBO);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        pb.setVisibility(View.GONE);

        mMap = googleMap;
        mMap.setMinZoomPreference(Maps.MAP_ZOOM_MIN);
        mMap.setMaxZoomPreference(Maps.MAP_ZOOM_MAX);

/*
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        */


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

    private void initGeolocationProcess(){
        if(!isLocationEnabled()){
            showRequestEnableLocation();
        }else{
            if (!isGrantedPermissionAccessLocation()){
                requestPermissionAccessLocation();
            }else {
                getCoordsCurrentPosition();
            }
        }
    }

    private void getGeolocation(double lat, double lng) {
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
        refPositionBO.setCountry(mAddress.get(0).getCountryName());
    }

    @Override
    public void setRefPosition(RefPositionBO refPositionBO, boolean isChanged) {
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
        if (mMarker == null) {
            MarkerOptions mMarkerOptions = new MarkerOptions();
            mMarkerOptions
                    .position(currentPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_position_bold_blue_96))
                    .draggable(true)
                    .title(getString(R.string.current_position))
                    .snippet(refPositionBO.getAddress());
            mMarker = mMap.addMarker(mMarkerOptions);
        } else {
            mMarker.setPosition(currentPosition);
            mMarker.setSnippet(refPositionBO.getAddress());
        }
        mMarker.showInfoWindow();
    }

    private boolean isLocationEnabled() {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showRequestEnableLocationDenied() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.denied_enable_location);
        mBuilder.setMessage(R.string.request_location_denied);
        mBuilder.setPositiveButton(R.string.go_to_granted_permission_location, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestEnableLocation();
            }
        });
        mBuilder.setNegativeButton(R.string.close_map, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToRefPosition();
            }
        });
        mBuilder.show();
    }

    public void getCoordsCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            setCurrentPositionMap(location.getLatitude(), location.getLongitude());
                        }
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_FINE_LOCATION:
                if(grantResults[0] == PERMISISION_FINE_LOCATION_DENIED){
                    showPermissionFineLocationDeniedDialog();
                }else{
                    getCoordsCurrentPosition();
                }
                break;
            default:
                break;
        }
    }

    public void showPermissionFineLocationDeniedDialog() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.denied_permission_access_location);
        mBuilder.setMessage(R.string.request_permission_access_location);
        mBuilder.setPositiveButton(R.string.go_to_granted_permission_location, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissionAccessLocation();
            }
        });
        mBuilder.setNegativeButton(R.string.close_map, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToRefPosition();
            }
        });
        mBuilder.show();
    }

    private void goToRefPosition() {
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
