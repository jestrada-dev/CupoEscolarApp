package co.jestrada.cupoescolarapp.attendant.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Settings;
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
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.attendant.presenter.CurrentPositionMapPresenter;

public class CurrentPositionMapActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.btn_set_current_position)
    Button btnSetCurrentPosition;

    private Geocoder mGeocoder;
    private List<Address> mAddress;
    private MarkerOptions mMarkerOptions;

    private GoogleMap mMap;

    private CurrentPositionMapPresenter mCurrentPositionMapPresenter;
    private RefPositionBO refPositionBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_position_map);

        ButterKnife.bind(this);
        pb.setVisibility(View.VISIBLE);

        try {
            int gpsSignal = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        mCurrentPositionMapPresenter = new CurrentPositionMapPresenter(CurrentPositionMapActivity.this);
        refPositionBO = new RefPositionBO();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @OnClick(R.id.btn_set_current_position)
    public void setCurrentPosition(){
        mCurrentPositionMapPresenter.saveRefPosition(refPositionBO);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        pb.setVisibility(View.GONE);

        mMap = googleMap;
        mMap.setMinZoomPreference(Maps.MAP_ZOOM_MIN);
        mMap.setMaxZoomPreference(Maps.MAP_ZOOM_MAX);

        mGeocoder = new Geocoder(this, Locale.getDefault());

        LatLng currentPositionCoords = new LatLng(10.9275775, -74.7954421);
        mMarkerOptions = new MarkerOptions();
        mMarkerOptions.position(currentPositionCoords);
        mMarkerOptions.title(getString(R.string.current_position));
        mMarkerOptions.draggable(true);
        mMarkerOptions.snippet("Esto es una caja de texto para modificar");
        mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_circle));

        mMap.addMarker(mMarkerOptions);

        CameraPosition mCameraPosition = new CameraPosition.Builder()
                .target(currentPositionCoords)
                .zoom(Maps.MAP_ZOOM)
                .tilt(Maps.MAP_TILT)
                .bearing(Maps.MAP_BEARING)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                try {
                    double lat = marker.getPosition().latitude;
                    double lng = marker.getPosition().longitude;
                    mAddress = mGeocoder.getFromLocation(lat, lng, 1);
                    setRefPoint(mAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setRefPoint(List<Address> mAddress) {
        refPositionBO.setLat(mAddress.get(0).getLatitude());
        refPositionBO.setLng(mAddress.get(0).getLongitude());
        refPositionBO.setAddress(mAddress.get(0).getAddressLine(0));
        refPositionBO.setCity(mAddress.get(0).getLocality());
        refPositionBO.setAdminArea(mAddress.get(0).getAdminArea());
        refPositionBO.setPostalCode(mAddress.get(0).getPostalCode());
    }
}
