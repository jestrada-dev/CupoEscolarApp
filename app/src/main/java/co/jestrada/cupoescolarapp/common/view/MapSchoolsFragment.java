package co.jestrada.cupoescolarapp.common.view;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.location.constant.ConstantsMap;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;


public class MapSchoolsFragment extends Fragment implements OnMapReadyCallback {

    List<SchoolOrderedByRefPositionBO> schools;
    RefPositionBO refPosition;
    private DataListenerMaps callback;

    private Marker mMarker;

    private GoogleMap mMap;

    private View rootView;
    private MapView mapView;

    public MapSchoolsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback = (DataListenerMaps) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_closest_schools, container, false);

        schools = new ArrayList<>();
        refPosition = new RefPositionBO();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.mapSchools);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    public void setListSchools(List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS,
                               RefPositionBO refPositionBO){
        schools = schoolOrderedByRefPositionBOS;
        refPosition = refPositionBO;
        setUserMarkerMap();
        setCameraMap();
/*        int index = 0;
        for (index = 0; index < 5; index++){
            addSchoolMarker(schoolOrderedByRefPositionBOS.get(index));
        }*/
        for (SchoolOrderedByRefPositionBO school : schoolOrderedByRefPositionBOS){
            addSchoolMarker(school);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMap();
        callback.getSchoolsMap();
    }


    private void setMap() {
        mMap.setMinZoomPreference(ConstantsMap.MAP_ZOOM_MIN);
        mMap.setMaxZoomPreference(ConstantsMap.MAP_ZOOM_MAX);
    }

    private void addSchoolMarker(SchoolOrderedByRefPositionBO school) {
        LatLng schoolPosition = new LatLng(school.getLat(), school.getLng());
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions
                .position(schoolPosition)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_school_bold_blue_96))
                .draggable(false)
                .title("Distancia: " + school.getDistanceText() + " Tiempo: " + school.getDurationText())
                .snippet(school.getName());
        mMarker = mMap.addMarker(mMarkerOptions);
        mMarker.showInfoWindow();
    }

    private void setUserMarkerMap() {
        LatLng currentPosition = new LatLng(refPosition.getLat(), refPosition.getLng());
        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions
                .position(currentPosition)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_position_bold_blue_96))
                .draggable(false)
                .title(refPosition.getDescription())
                .snippet(refPosition.getAddress());
        mMarker = mMap.addMarker(mMarkerOptions);
        mMarker.showInfoWindow();
    }

    private void setCameraMap() {
        LatLng currentPosition = new LatLng(refPosition.getLat(), refPosition.getLng());
        CameraPosition mCameraPosition = new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(ConstantsMap.MAP_ZOOM)
                .tilt(ConstantsMap.MAP_TILT)
                .bearing(ConstantsMap.MAP_BEARING)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
    }


    public interface DataListenerMaps{
        void getSchoolsMap();
    }
}
