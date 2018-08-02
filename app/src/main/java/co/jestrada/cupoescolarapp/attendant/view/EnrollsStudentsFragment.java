package co.jestrada.cupoescolarapp.attendant.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.jestrada.cupoescolarapp.R;

public class EnrollsStudentsFragment extends Fragment {

    public EnrollsStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enrolls_students, container, false);
    }

}
