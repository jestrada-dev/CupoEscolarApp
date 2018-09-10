package co.jestrada.cupoescolarapp.student.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.view.ConfigAccountActivity;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.student.adapter.StudentsAdapter;
import co.jestrada.cupoescolarapp.student.contract.IStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.presenter.StudentPresenter;

public class StudentsActivity extends BaseActivity implements
        IStudentContract.IStudentView{

    private StudentPresenter mStudentPresenter;

    @BindView(R.id.fav_add_student)
    FloatingActionButton addStudent;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<StudentBO> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_students);

        mStudentPresenter = new StudentPresenter(StudentsActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_students);
        mLayoutManager = new LinearLayoutManager(this);

        students = new ArrayList<>();
/*        StudentBO studentBO = new StudentBO();
        studentBO.setFirstName("Alejandro");
        studentBO.setLastName("Estrada Sierra");
        studentBO.setDocId("72345567");
        students.add(studentBO);*/

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        setToolbar();
        getData();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);

            getSupportActionBar().setTitle(R.string.students);
            getSupportActionBar().setSubtitle("Agrega o edita tus estudiantes");
            mToolbar.setTitleTextAppearance(this, R.style.TextTitle);
            mToolbar.setTitleTextColor(getColor(R.color.mColorPrimaryText));
            mToolbar.setSubtitleTextAppearance(this, R.style.TextSubtitle1);
            mToolbar.setSubtitleTextColor(getColor(R.color.mColorSecondaryText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_blue_48);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

    }

    private void getData(){
        showProgressBar(true);
        mStudentPresenter.getData();
    }

    @OnClick(R.id.fav_add_student)
    public void addStudent(){
        goToAddEditStudent(null);
    }


    private void goToAddEditStudent(String docIdStudent) {
        Intent intent = new Intent(this, AddEditStudentActivity.class);
        if(docIdStudent != null){
            intent.putExtra("docIdStudent", docIdStudent);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void getStudentTransactionState(boolean successful) {
        showProgressBar(false);
    }

    @Override
    public void setStudentsUI(ArrayList<StudentBO> studentBOS, boolean isChanged) {
        showProgressBar(false);
        if(isChanged){
            students.clear();
            students = studentBOS;
            mAdapter = new StudentsAdapter(students, R.layout.students_item, new StudentsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(StudentBO studentBO, int position) {
                    goToAddEditStudent(studentBO.getDocId());
                }
            });
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            //mAdapter.notifyDataSetChanged();

        }
    }

}
