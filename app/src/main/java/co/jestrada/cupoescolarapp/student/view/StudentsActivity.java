package co.jestrada.cupoescolarapp.student.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.presenter.EditProfilePresenter;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.student.contract.IStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.presenter.StudentPresenter;

public class StudentsActivity extends BaseActivity implements
        IStudentContract.IStudentView{

    private StudentPresenter mStudentPresenter;

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
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_48);
            //TODO Establecer el botón de atrás
        }
    }

    private void getData(){
        showProgressBar(true);
        mStudentPresenter.getData();
    }

    @Override
    public void getStudentTransactionState(boolean successful) {

    }

    @Override
    public void setStudentsUI(ArrayList<StudentBO> studentBOS, boolean isChanged) {

    }

}
