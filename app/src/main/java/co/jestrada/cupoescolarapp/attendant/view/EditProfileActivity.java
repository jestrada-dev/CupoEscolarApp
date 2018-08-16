package co.jestrada.cupoescolarapp.attendant.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
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
import co.jestrada.cupoescolarapp.attendant.contract.IEditProfileContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.enums.GenreEnum;
import co.jestrada.cupoescolarapp.attendant.presenter.EditProfilePresenter;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;

public class EditProfileActivity extends BaseActivity implements
        IEditProfileContract.IEditProfileView{

    private EditProfilePresenter mEditProfilePresenter;

    private static final String ZERO = "0";
    private static final String SEPARATOR = "-";

    public final Calendar mCalendar = Calendar.getInstance();
    final int month = mCalendar.get(Calendar.MONTH);
    final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
    final int year = mCalendar.get(Calendar.YEAR);

    private Toolbar mToolbar;

    private ArrayList<String> docIdTypeArrayList;
    private ArrayList<String> docIdTypeShortNameArrayList;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.et_doc_id_type)
    EditText etDocIdType;
    @BindView(R.id.et_doc_id)
    EditText etDocId;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.et_birthdate)
    EditText etBirthday;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_local_phone)
    EditText etLocalPhone;
    @BindView(R.id.et_mobile_phone)
    EditText etMobilPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mEditProfilePresenter = new EditProfilePresenter(EditProfileActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

        docIdTypeArrayList = new ArrayList<>();
        docIdTypeShortNameArrayList = new ArrayList<>();

    }

    private void initView() {
        ButterKnife.bind(this);
        setToolbar();
        getData();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.edit_profile);
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_48);
            //TODO Establecer el botón de atrás
        }
    }

    private void getData(){
        enableFields(false);
        showProgressBar(true);
        mEditProfilePresenter.getData();
    }

    @OnClick(R.id.btn_save)
    public void save(){
        if(validateInputs()){
            showProgressBar(true);
            enableFields(false);
            saveAttendant();
        }

    }

    private boolean validateInputs() {
        boolean validInputs = true;

        if(!isValidPhone(etMobilPhone.getText().toString().trim())){
            validInputs = false;
            etMobilPhone.setError(getString(R.string.validate_input_mobile_phone));
            etMobilPhone.requestFocus();
        }

        if(!isValidPhone(etLocalPhone.getText().toString().trim())){
            validInputs = false;
            etLocalPhone.setError(getString(R.string.validate_input_local_phone));
            etLocalPhone.requestFocus();
        }

        if(!isValidDocId(etDocId.getText().toString().trim())){
            validInputs = false;
            etLocalPhone.setError(getString(R.string.validate_input_doc_id));
            etLocalPhone.requestFocus();
        } else {
            if(!isValidDocIdType(etDocIdType.getText().toString().trim())){
                validInputs = false;
                etDocIdType.setError(getString(R.string.validate_input_doc_id_type));
                etDocIdType.requestFocus();
            }
        }

        return  validInputs;
    }

    private boolean isValidPhone(String phone){
        boolean err = false;
        if (!TextUtils.isEmpty(phone)){
            if (!Patterns.PHONE.matcher(phone).matches()){
                err = true;
            }
        }
        return !err;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle(R.string.select_doc_id);
        final ArrayAdapter<String> docIdTypeBOArrayAdapter = new ArrayAdapter<>(
                EditProfileActivity.this, android.support.design.R.layout.select_dialog_singlechoice_material);
        docIdTypeBOArrayAdapter.addAll(docIdTypeArrayList);
        builder.setAdapter(docIdTypeBOArrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                etDocIdType.setText(docIdTypeShortNameArrayList.get(item));
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
                etBirthday.setText(currentDay + SEPARATOR + buildCurrentMonth + SEPARATOR + year);
            }
        },year, month, day);
        mDatePickerDialog.show();

    }

    private void saveAttendant() {
        AttendantBO attendantBO = new AttendantBO();
        attendantBO.setDocIdType(etDocIdType.getText() != null ? etDocIdType.getText().toString() : "");
        attendantBO.setDocId(etDocId.getText() != null ? etDocId.getText().toString() : "");
        attendantBO.setFirstName(etFirstName.getText() != null ? etFirstName.getText().toString() : "");
        attendantBO.setLastName(etLastName.getText() != null ? etLastName.getText().toString() : "");
        if(rbMale.isChecked()){
            attendantBO.setGenre(GenreEnum.HOMBRE);
        } else
        if(rbFemale.isChecked()){
            attendantBO.setGenre(GenreEnum.MUJER);
        }
        attendantBO.setBirthdate(etBirthday.getText() != null ? etBirthday.getText().toString() : "");
        attendantBO.setEmail(etEmail.getText() != null ? etEmail.getText().toString() : "");
        attendantBO.setAddress(etAddress.getText() != null ? etAddress.getText().toString() : "");
        attendantBO.setLocalPhone(etLocalPhone.getText() != null ? etLocalPhone.getText().toString() : "");
        attendantBO.setMobilePhone(etMobilPhone.getText() != null ? etMobilPhone.getText().toString() : "");
        mEditProfilePresenter.saveAttendant(attendantBO);
    }

    void enableFields(boolean enable){
        btnSave.setEnabled(enable);
        enableFieldsPersonalInfo(enable);
        enableFieldsContactInfo(enable);
    }

    void enableFieldsPersonalInfo(boolean enable){
        etDocIdType.setEnabled(enable);
        etDocId.setEnabled(enable);
        etFirstName.setEnabled(enable);
        etLastName.setEnabled(enable);
        rbMale.setEnabled(enable);
        rbFemale.setEnabled(enable);
        etBirthday.setEnabled(enable);
    }

    void enableFieldsContactInfo(boolean enable){
        etEmail.setEnabled(enable);
        etAddress.setEnabled(enable);
        etLocalPhone.setEnabled(enable);
        etMobilPhone.setEnabled(enable);
    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void getAttendantTransactionState(boolean successful) {
        if(successful){
            Toast.makeText(this, R.string.attendan_profile_updated,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setAttendantUI(AttendantBO attendantBO, boolean isChanged) {
        if (isChanged){
            etDocIdType.setText((attendantBO.getDocIdType() != null) ? attendantBO.getDocIdType() : "");
            etDocId.setText((attendantBO.getDocId() != null) ? attendantBO.getDocId() : "");
            etFirstName.setText((attendantBO.getFirstName() != null) ? attendantBO.getFirstName() : "");
            etLastName.setText((attendantBO.getLastName() != null) ? attendantBO.getLastName() : "");
            etFirstName.setText((attendantBO.getFirstName() != null) ? attendantBO.getFirstName() : "");
            if(attendantBO.getGenre() != null){
                if(attendantBO.getGenre().equals(GenreEnum.HOMBRE)){
                    rbMale.setChecked(true);
                }
                else{
                    rbFemale.setChecked(true);
                }
            }
            etBirthday.setText((attendantBO.getBirthdate() != null) ? attendantBO.getBirthdate() : "");
            etEmail.setText((attendantBO.getEmail() != null) ? attendantBO.getEmail() : "");
            etAddress.setText((attendantBO.getAddress() != null) ? attendantBO.getAddress() : "");
            etLocalPhone.setText((attendantBO.getLocalPhone() != null) ? attendantBO.getLocalPhone() : "");
            etMobilPhone.setText((attendantBO.getMobilePhone() != null) ? attendantBO.getMobilePhone() : "");
        }
        showProgressBar(false);
        enableFields(true);
        etDocId.requestFocus();
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
            Toast.makeText(this, R.string.doc_type_updated,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
