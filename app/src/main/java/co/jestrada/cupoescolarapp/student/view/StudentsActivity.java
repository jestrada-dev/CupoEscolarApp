package co.jestrada.cupoescolarapp.student.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.view.ConfigAccountActivity;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.student.contract.IStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.presenter.StudentPresenter;

public class StudentsActivity extends BaseActivity implements
        IStudentContract.IStudentView{

    private StudentPresenter mStudentPresenter;

    @BindView(R.id.cv_students)
    CardView cvStudent;
    @BindView(R.id.tv_student_name)
    TextView tvStudentName;
    @BindView(R.id.tv_doc_id_type)
    TextView tvDocIdType;
    @BindView(R.id.tv_doc_id)
    TextView tvDocId;
    @BindView(R.id.iv_edit_student)
    ImageView ivEditStudent;


    @BindView(R.id.fav_add_student)
    FloatingActionButton addStudent;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_students);

        mStudentPresenter = new StudentPresenter(StudentsActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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


    @OnClick(R.id.iv_edit_student)
    public void goToStudent(){
        goToAddEditStudent(tvDocId.getText().toString());
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
            tvStudentName.setText(studentBOS.get(0).getFirstName() + " " + studentBOS.get(0).getLastName());
            tvDocIdType.setText(studentBOS.get(0).getDocIdType());
            tvDocId.setText(studentBOS.get(0).getDocId());
        }
    }

}
