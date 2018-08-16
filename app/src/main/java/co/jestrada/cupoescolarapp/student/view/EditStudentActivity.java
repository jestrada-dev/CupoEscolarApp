package co.jestrada.cupoescolarapp.student.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.student.contract.IEditStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.presenter.EditStudentPresenter;

public class EditStudentActivity extends BaseActivity implements
        IEditStudentContract.IEditStudentView{

    @BindView(R.id.til_doc_id_type)
    TextInputLayout tilDocIdType;
    @BindView(R.id.et_doc_id_type)
    EditText etDocIdType;
    @BindView(R.id.til_doc_id)
    TextInputLayout tilDocId;
    @BindView(R.id.et_doc_id)
    EditText etDocId;
    @BindView(R.id.til_first_name)
    TextInputLayout tilFirstName;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.til_birthdate)
    TextInputLayout tilBirthdate;
    @BindView(R.id.et_birthdate)
    EditText etBirthdate;

    @BindView(R.id.til_grade)
    TextInputLayout tilGrade;
    @BindView(R.id.et_grade)
    EditText etGrade;
    @BindView(R.id.btn_save)
    Button btnSave;

    private EditStudentPresenter mEditStudentPresenter;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        mEditStudentPresenter = new EditStudentPresenter(EditStudentActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

        getData();
    }

    private String getDocIdStudent() {
        return "";
    }

    private void initView() {
        ButterKnife.bind(this);
        setToolbar();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.edit_student);
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_48);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    private void getData(){
        enableInputs(false);
        showProgressBar(true);
        mEditStudentPresenter.getData(getDocIdStudent());
    }

    private void enableInputs(boolean enable) {
        etDocIdType.setEnabled(enable);
        etDocId.setEnabled(enable);
        etFirstName.setEnabled(enable);
        etLastName.setEnabled(enable);
        rbMale.setEnabled(enable);
        rbFemale.setEnabled(enable);
        etBirthdate.setEnabled(enable);
        etGrade.setEnabled(enable);
    }

    @OnClick(R.id.btn_save)
    public void saveStudent(){
        showProgressBar(true);
        enableInputs(false);
        StudentBO studentBO = new StudentBO();

        mEditStudentPresenter.saveStudent(studentBO);
    }

    @Override
    public void getStudentTransactionState(boolean successful) {

    }

    @Override
    public void setStudentUI(StudentBO studentBO, boolean isChanged) {

    }
}
