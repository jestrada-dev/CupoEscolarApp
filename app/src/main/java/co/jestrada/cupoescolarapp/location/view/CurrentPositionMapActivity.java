package co.jestrada.cupoescolarapp.location.view;

import android.Manifest;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.location.constant.ConstantsMap;
import co.jestrada.cupoescolarapp.location.contract.ICurrentPositionMapContract;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.location.presenter.CurrentPositionMapPresenter;

public class CurrentPositionMapActivity extends FragmentActivity implements
        ICurrentPositionMapContract.ICurrentPositionMapView,
        OnMapReadyCallback {

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.fav_save)
    FloatingActionButton favSave;
    @BindView(R.id.fav_update_position)
    FloatingActionButton favUpdatePosition;

    LocationManager mLocationManager;
    private List<Address> mAddress;
    private Marker mMarker;

    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationClient;

    private CurrentPositionMapPresenter mCurrentPositionMapPresenter;
    private RefPositionBO refPositionBO;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_position_map);

        initView();

        mCurrentPositionMapPresenter = new CurrentPositionMapPresenter(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        refPositionBO = new RefPositionBO();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }

    }

    private void initView() {
        ButterKnife.bind(this);
        enableBtnSetCurrentPosition(false);
        pb.setVisibility(View.VISIBLE);
    }

    private void enableBtnSetCurrentPosition(boolean enable) {
        favSave.setEnabled(enable);
    }

    @OnClick(R.id.fav_save)
    public void save() {
        pb.setVisibility(View.VISIBLE);
        mCurrentPositionMapPresenter.saveRefPositionNoDescription(refPositionBO);
    }

    @OnClick(R.id.fav_update_position)
    public void getCurrentPosition() {
        mapFragment.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        pb.setVisibility(View.GONE);

        initGeolocationProcess();

        mMap = googleMap;
        setMap();
        setMarker();

    }

    private void setMap() {
        mMap.setMinZoomPreference(ConstantsMap.MAP_ZOOM_MIN);
        mMap.setMaxZoomPreference(ConstantsMap.MAP_ZOOM_MAX);
    }

    private void setMarker() {
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
        if (!isGrantedPermissionAccessLocation()){
            requestPermissionAccessLocation();
        }else {
            getCoordsCurrentPosition();
        }
    }

    private void requestPermissionAccessLocation(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ConstantsMap.REQUEST_FINE_LOCATION);
    }

    private boolean isGrantedPermissionAccessLocation() {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    private void setUserMarkerMap(double lat, double lng) {
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

    private void setCameraMap(double lat, double lng) {
        LatLng currentPosition = new LatLng(lat, lng);
        CameraPosition mCameraPosition = new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(ConstantsMap.MAP_ZOOM)
                .tilt(ConstantsMap.MAP_TILT)
                .bearing(ConstantsMap.MAP_BEARING)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
    }

    private boolean isLocationEnabled() {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

/*    private void showRequestEnableLocationDenied() {
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
    }*/

    public void getCoordsCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLocationAvailability().addOnCompleteListener(new OnCompleteListener<LocationAvailability>() {
            @Override
            public void onComplete(@NonNull Task<LocationAvailability> task) {
                String nose = "";
            }
        });

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            setCurrentPositionMap(location.getLatitude(), location.getLongitude());
                            enableBtnSetCurrentPosition(true);
                        } else {
                            if (!isLocationEnabled()){
                                showRequestEnableLocation();
                            } else {
                                showFailedGeolocation();
                            }
                        }
                    }
                });

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
    }

    private void showFailedGeolocation() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.location_failed);
        mBuilder.setMessage(R.string.location_failed_summary);
        mBuilder.setNegativeButton(R.string.try_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToRefPosition();
            }
        });
        mBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case ConstantsMap.REQUEST_FINE_LOCATION:
                switch (grantResults[0]) {
                    case ConstantsMap.PERMISISION_FINE_LOCATION_DENIED:
                        showPermissionFineLocationDeniedDialog();
                        break;
                    case ConstantsMap.PERMISISION_FINE_LOCATION_GRANTED:
                        getCoordsCurrentPosition();
                        break;
                    default:
                        break;
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

    @Override
    public void getRefPositionTransactionState(boolean successful) {

    }

    @Override
    public void setRefPosition(RefPositionBO refPositionBO, boolean isChanged) {
        pb.setVisibility(View.GONE);
        goToRefPosition();
    }

    public void setCurrentPositionMap(double lat, double lng) {
        getGeolocation(lat, lng);
        setRefPoint();
        setUserMarkerMap(lat, lng);
        setCameraMap(lat, lng);
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
