package co.jestrada.cupoescolarapp.common.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.jestrada.cupoescolarapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClosestSchoolsFragment extends Fragment {


    public ClosestSchoolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closest_schools, container, false);
    }

}
