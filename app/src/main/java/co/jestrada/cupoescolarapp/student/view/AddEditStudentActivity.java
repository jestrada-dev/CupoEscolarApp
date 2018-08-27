package co.jestrada.cupoescolarapp.student.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.common.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.common.model.bo.GradeBO;
import co.jestrada.cupoescolarapp.common.model.bo.RelationshipTypeBO;
import co.jestrada.cupoescolarapp.common.model.enums.GenreEnum;
import co.jestrada.cupoescolarapp.student.contract.IAddEditStudentContract;
import co.jestrada.cupoescolarapp.student.model.bo.StudentBO;
import co.jestrada.cupoescolarapp.student.presenter.AddEditStudentPresenter;

public class AddEditStudentActivity extends BaseActivity implements
        IAddEditStudentContract.IAddEditStudentView {

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
    @BindView(R.id.til_relationship)
    TextInputLayout tilRelationship;
    @BindView(R.id.et_relationship)
    EditText etRelationship;

    @BindView(R.id.til_grade)
    TextInputLayout tilGrade;
    @BindView(R.id.et_grade)
    EditText etGrade;

    @BindView(R.id.fav_save)
    FloatingActionButton favSave;
    @BindView(R.id.fav_edit)
    FloatingActionButton favEdit;
    @BindView(R.id.fav_cancel)
    FloatingActionButton favCancel;

    private AddEditStudentPresenter mAddEditStudentPresenter;

    private static final String ZERO = "0";
    private static final String SEPARATOR = "-";

    public final Calendar mCalendar = Calendar.getInstance();
    final int month = mCalendar.get(Calendar.MONTH);
    final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
    final int year = mCalendar.get(Calendar.YEAR);

    private Toolbar mToolbar;

    private ArrayList<String> docIdTypeArrayList;
    private ArrayList<String> docIdTypeShortNameArrayList;

    private ArrayList<String> gradeArrayList;
    private ArrayList<String> gradeNumberArrayList;

    private ArrayList<String> relationshipNameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_student);

        mAddEditStudentPresenter = new AddEditStudentPresenter(AddEditStudentActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

        getData();

        docIdTypeArrayList = new ArrayList<>();
        docIdTypeShortNameArrayList = new ArrayList<>();

        gradeArrayList = new ArrayList<>();
        gradeNumberArrayList = new ArrayList<>();

        relationshipNameArrayList = new ArrayList<>();


    }

    private String getDocIdStudent() {
        return getIntent().getStringExtra("docIdStudent");
    }

    private void initView() {
        ButterKnife.bind(this);
        setToolbar();

    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.edit_student);
            getSupportActionBar().setSubtitle("Registra los datos personales y el grado a solicitar");
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

    private void getData(){
        enableInputs(false);
        showProgressBar(true);
        mAddEditStudentPresenter.getData(getDocIdStudent());
    }

    private void enableInputs(boolean enable) {
        etDocIdType.setEnabled(enable);
        etDocId.setEnabled(enable);
        etFirstName.setEnabled(enable);
        etLastName.setEnabled(enable);
        rbMale.setEnabled(enable);
        rbFemale.setEnabled(enable);
        etBirthdate.setEnabled(enable);
        etRelationship.setEnabled(enable);
        etGrade.setEnabled(enable);
    }

    private boolean validateInputs() {
        boolean validInputs = true;

        if(!isValidDocId(etDocId.getText().toString().trim())){
            validInputs = false;
            etDocId.setError(getString(R.string.validate_input_doc_id));
            etDocId.requestFocus();
        } else {
            if(!isValidDocIdType(etDocIdType.getText().toString().trim())){
                validInputs = false;
                etDocIdType.setError(getString(R.string.validate_input_doc_id_type));
                etDocIdType.requestFocus();
            }
        }

        return  validInputs;
    }

    private boolean isValidDocId(String docId){
        boolean err = false;
        if (!TextUtils.isEmpty(docId)){
            if (!TextUtils.isDigitsOnly(docId)){
                err = true;
            }
        }
        return !err;
    }

    private boolean isValidDocIdType(String docIdType){
        boolean err = false;
        if (TextUtils.isEmpty(docIdType)){
            err = true;
        }
        return !err;
    }


    @OnClick(R.id.et_doc_id_type)
    public void showDocIdTypeList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditStudentActivity.this);
        builder.setTitle(R.string.select_doc_id);
        final ArrayAdapter<String> docIdTypeBOArrayAdapter = new ArrayAdapter<>(
                AddEditStudentActivity.this, android.support.design.R.layout.select_dialog_singlechoice_material);
        docIdTypeBOArrayAdapter.addAll(docIdTypeArrayList);
        builder.setAdapter(docIdTypeBOArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                etDocIdType.setText(docIdTypeShortNameArrayList.get(item));
                dialog.dismiss();
            }
        }).show();
    }

    @OnClick(R.id.et_relationship)
    public void showRelationshipList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditStudentActivity.this);
        builder.setTitle("Selecciona el parentesco");
        final ArrayAdapter<String> relationshipTypeBOArrayAdapter = new ArrayAdapter<>(
                AddEditStudentActivity.this, android.support.design.R.layout.select_dialog_singlechoice_material);
        relationshipTypeBOArrayAdapter.addAll(relationshipNameArrayList);
        builder.setAdapter(relationshipTypeBOArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                etRelationship.setText(relationshipNameArrayList.get(item));
                dialog.dismiss();
            }
        }).show();
    }

    @OnClick(R.id.et_birthdate)
    public void getDate(){
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int currentMonth = month + 1;
                String currentDay = (dayOfMonth < 10)? ZERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String buildCurrentMonth = (currentMonth < 10)? ZERO + String.valueOf(currentMonth):String.valueOf(currentMonth);
                etBirthdate.setText(currentDay + SEPARATOR + buildCurrentMonth + SEPARATOR + year);
            }
        },year, month, day);
        mDatePickerDialog.show();

    }

    @OnClick(R.id.et_grade)
    public void showGradeList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditStudentActivity.this);
        builder.setTitle("Selecciona el grado");
        final ArrayAdapter<String> gradeBOArrayAdapter = new ArrayAdapter<>(
                AddEditStudentActivity.this, android.support.design.R.layout.select_dialog_singlechoice_material);
        gradeBOArrayAdapter.addAll(gradeArrayList);
        builder.setAdapter(gradeBOArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                etGrade.setText(gradeNumberArrayList.get(item));
                dialog.dismiss();
            }
        }).show();
    }

    @OnClick(R.id.fav_edit)
    public void edit(){
        enableInputs(true);
        favEdit.hide();
        favSave.show();
        favCancel.show();
    }

    @OnClick(R.id.fav_cancel)
    public void cancel(){
        enableInputs(false);
        favEdit.show();
        favSave.hide();
        favCancel.hide();
    }

    @OnClick(R.id.fav_save)
    public void save() {
        if (validateInputs()) {
            favEdit.show();
            favSave.hide();
            favCancel.hide();
            showProgressBar(true);
            enableInputs(false);
            saveStudent();
        }
    }

    private void saveStudent(){
        StudentBO studentBO = new StudentBO();
        studentBO.setDocIdType(etDocIdType.getText() != null ? etDocIdType.getText().toString() : "");
        studentBO.setDocId(etDocId.getText() != null ? etDocId.getText().toString() : "");
        studentBO.setFirstName(etFirstName.getText() != null ? etFirstName.getText().toString() : "");
        studentBO.setLastName(etLastName.getText() != null ? etLastName.getText().toString() : "");
        if(rbMale.isChecked()){
            studentBO.setGenre(GenreEnum.HOMBRE);
        } else
        if(rbFemale.isChecked()){
            studentBO.setGenre(GenreEnum.MUJER);
        }
        studentBO.setBirthdate(etBirthdate.getText() != null ? etBirthdate.getText().toString() : "");
        studentBO.setRelationship(etRelationship.getText() != null ? etRelationship.getText().toString() : "");
        studentBO.setGrade(etGrade.getText() != null ? etGrade.getText().toString() : "");

        mAddEditStudentPresenter.saveStudent(studentBO);
    }

    @Override
    public void getStudentTransactionState(boolean successful) {
        if(successful){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_add_edit_student), "Estudiante agregado exitosamente", Snackbar.LENGTH_LONG)
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
    public void setStudentUI(StudentBO studentBO, boolean isChanged) {
        if (isChanged){
            etDocIdType.setText((studentBO.getDocIdType() != null) ? studentBO.getDocIdType() : "");
            etDocId.setText((studentBO.getDocId() != null) ? studentBO.getDocId() : "");
            etFirstName.setText((studentBO.getFirstName() != null) ? studentBO.getFirstName() : "");
            etLastName.setText((studentBO.getLastName() != null) ? studentBO.getLastName() : "");
            etFirstName.setText((studentBO.getFirstName() != null) ? studentBO.getFirstName() : "");
            if(studentBO.getGenre() != null){
                if(studentBO.getGenre().equals(GenreEnum.HOMBRE)){
                    rbMale.setChecked(true);
                }
                else{
                    rbFemale.setChecked(true);
                }
            }
            etBirthdate.setText((studentBO.getBirthdate() != null) ? studentBO.getBirthdate() : "");
            etRelationship.setText((studentBO.getRelationship() != null) ? studentBO.getRelationship() : "");
            etGrade.setText((studentBO.getGrade() != null) ? studentBO.getGrade() : "");
        }
        showProgressBar(false);
    }

    @Override
    public void setDocIdTypesList(ArrayList<DocIdTypeBO> docIdTypeBOS, boolean isChanged) {
        if (isChanged){
            if (!docIdTypeBOS.isEmpty()){
                docIdTypeArrayList.clear();
                docIdTypeShortNameArrayList.clear();
                for (DocIdTypeBO docIdTypeBO : docIdTypeBOS){
                    docIdTypeArrayList.add(docIdTypeBO.getShortName() + " " + docIdTypeBO.getLongName());
                    docIdTypeShortNameArrayList.add(docIdTypeBO.getShortName());
                }
            }
        }
    }

    @Override
    public void setRelationshipTypesList(ArrayList<RelationshipTypeBO> relationshipTypeBOS, boolean isChanged) {
        if (isChanged){
            if (!relationshipTypeBOS.isEmpty()){
                relationshipNameArrayList.clear();
                for (RelationshipTypeBO relationshipTypeBO : relationshipTypeBOS){
                    relationshipNameArrayList.add(relationshipTypeBO.getName());
                }
            }
        }

    }

    @Override
    public void setGradesList(ArrayList<GradeBO> gradeBOS, boolean isChanged) {
        showProgressBar(false);
        enableInputs(true);
        if (isChanged){
            if (!gradeBOS.isEmpty()){
                gradeArrayList.clear();
                gradeNumberArrayList.clear();
                for (GradeBO gradeBO : gradeBOS){
                    gradeArrayList.add(gradeBO.getGrade() + " " + gradeBO.getName());
                    gradeNumberArrayList.add(gradeBO.getGrade());
                }
            }
        }

    }
}
