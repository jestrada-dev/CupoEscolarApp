package co.jestrada.cupoescolarapp.attendant.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import co.jestrada.cupoescolarapp.R;

public class CurrentPositionMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_position_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(14);
        mMap.setMaxZoomPreference(17);

        // Add a marker in Sydney and move the camera
        LatLng currentPositionCoords = new LatLng(10.9275775, -74.7954421);
        mMap.addMarker(new MarkerOptions()
                .position(currentPositionCoords)
                .title(getString(R.string.current_position))
                .draggable(true)
        );
        CameraPosition mCameraPosition = new CameraPosition.Builder()
                .target(currentPositionCoords)
                .zoom(17)       // limit -> 21 -> altura de la cámara
                .tilt(30)       // 0 - 365 -> relieve de los edificios
                .bearing(90)    // limit -> 90 -> ángulo de la cámara
                .build();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPositionCoords));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }
}
