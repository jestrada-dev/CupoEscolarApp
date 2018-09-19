package co.jestrada.cupoescolarapp.common.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.school.adapter.SchoolsAdapter;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolOrderedByRefPositionBO;
import co.jestrada.cupoescolarapp.school.view.SchoolActivity;

public class ListSchoolFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<SchoolOrderedByRefPositionBO> schools;

    private DataListener callback;

    public ListSchoolFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback = (DataListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_school_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_schools);
        mLayoutManager = new LinearLayoutManager(getContext());

        schools = new ArrayList<>();

        callback.getSchools();

        return view;
    }

    public void setListSchools(List<SchoolOrderedByRefPositionBO> schoolOrderedByRefPositionBOS){
        schools = new ArrayList<>();

        schools = schoolOrderedByRefPositionBOS;

        mAdapter = new SchoolsAdapter(schools, R.layout.schools_item, new SchoolsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SchoolOrderedByRefPositionBO school, int position) {
                goToSchool(school.getSchoolCode());
            }
        });
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void goToSchool(String schoolCode) {
        Intent intent = new Intent(getContext(), SchoolActivity.class);
        intent.putExtra("schoolCode", schoolCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    public interface DataListener{
        void getSchools();
    }

}
