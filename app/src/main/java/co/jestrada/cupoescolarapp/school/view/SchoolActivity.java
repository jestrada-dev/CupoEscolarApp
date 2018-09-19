package co.jestrada.cupoescolarapp.school.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.common.model.enums.GenreEnum;
import co.jestrada.cupoescolarapp.school.contract.SchoolContract;
import co.jestrada.cupoescolarapp.school.model.bo.SchoolBO;
import co.jestrada.cupoescolarapp.school.presenter.SchoolPresenter;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;

public class SchoolActivity extends BaseActivity implements SchoolContract.ISchoolView {

    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_director)
    EditText etDirector;
    @BindView(R.id.et_school_days)
    EditText etSchoolDays;
    @BindView(R.id.et_school_grades)
    EditText etSchoolGrades;
    @BindView(R.id.et_school_calendar)
    EditText etSchoolCalendar;
    @BindView(R.id.et_school_address)
    EditText etSchoolAddress;
    @BindView(R.id.et_school_email)
    EditText etSchoolEmail;
    @BindView(R.id.et_school_phones)
    EditText etSchoolPhones;
    @BindView(R.id.et_student)
    EditText etStudent;

    @BindView(R.id.fav_enroll_student)
    FloatingActionButton favEnrollStudent;

    private SchoolPresenter mSchoolPresenter;

    private Toolbar mToolbar;

    private ArrayList<String> studentArrayList;
    private ArrayList<String> studentNameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        mSchoolPresenter = new SchoolPresenter(SchoolActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        studentArrayList = new ArrayList<>();
        studentNameArrayList  = new ArrayList<>();

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
            getSupportActionBar().setTitle(R.string.school);
            getSupportActionBar().setSubtitle("Solicita tu cupo");
            mToolbar.setTitleTextAppearance(this, R.style.TextTitle);
            mToolbar.setTitleTextColor(getColor(R.color.mColorPrimaryText));
            mToolbar.setSubtitleTextAppearance(this, R.style.TextSubtitle1);
            mToolbar.setSubtitleTextColor(getColor(R.color.mColorSecondaryText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_blue_48);;
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        }
    }

    private String getSchoolCode() {
        return getIntent().getStringExtra("schoolCode");
    }


    private void getData(){
        showProgressBar(true);
        mSchoolPresenter.getData(getSchoolCode());
    }

    @OnClick(R.id.fav_enroll_student)
    public void enrollStudent() {
        showProgressBar(true);
        String schoolCode = etCode.getText().toString();
        String studentDocId = etStudent.getText().toString();
        mSchoolPresenter.enrollStudent(schoolCode, studentDocId);
    }

    @OnClick(R.id.et_student)
    public void showStudentsList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolActivity.this);
        builder.setTitle(R.string.select_student);
        final ArrayAdapter<String> studentBOArrayAdapter = new ArrayAdapter<>(
                SchoolActivity.this, android.support.design.R.layout.select_dialog_singlechoice_material);
        studentBOArrayAdapter.addAll(studentNameArrayList);
        builder.setAdapter(studentBOArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                etStudent.setText(studentArrayList.get(item));
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void getEnrollStudentTransactionState(boolean successful) {
        if(successful){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_school), "Estudiante inscrito exitosamente", Snackbar.LENGTH_LONG)
                    .setActionTextColor(getColor(R.color.mColorPrimaryText))
                    .setAction("Action", null);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getColor(R.color.mColorPrimaryLight));
            TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        }
        showProgressBar(false);
    }

    @Override
    public void setStudentList(ArrayList<StudentBO> studentBO, boolean isChanged) {
        if(isChanged){
            if (!studentBO.isEmpty()){
                studentArrayList.clear();
                studentNameArrayList.clear();
                for (StudentBO student : studentBO){
                    studentArrayList.add(student.getDocId());
                    studentNameArrayList.add(student.getDocId() + " " + student.getFirstName() + " " + student.getLastName());
                }
            }
        }
    }

    @Override
    public void setSchoolUI(SchoolBO schoolBO, boolean isChanged) {
        if (isChanged){
            etCode.setText((schoolBO.getCode() != null) ? schoolBO.getCode() : "");
            etName.setText((schoolBO.getName() != null) ? schoolBO.getName() : "");
            etDirector.setText((schoolBO.getDirector() != null) ? schoolBO.getDirector() : "");
            etSchoolDays.setText((schoolBO.getSchoolDays() != null) ? schoolBO.getSchoolDays() : "");
            etSchoolGrades.setText((schoolBO.getGrades() != null) ? schoolBO.getGrades() : "");
            etSchoolCalendar.setText((schoolBO.getCalendar() != null) ? schoolBO.getCalendar() : "");
            etSchoolAddress.setText((schoolBO.getAddress() != null) ? schoolBO.getAddress() : "");
            etSchoolPhones.setText((schoolBO.getPhones() != null) ? schoolBO.getPhones() : "");
            etSchoolEmail.setText((schoolBO.getEmail() != null) ? schoolBO.getEmail() : "");
        }
        showProgressBar(false);
    }
}
