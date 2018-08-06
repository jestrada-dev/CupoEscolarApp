package co.jestrada.cupoescolarapp.attendant.view;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.contract.IAttendantProfileContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPointBO;
import co.jestrada.cupoescolarapp.attendant.model.enums.GenreEnum;
import co.jestrada.cupoescolarapp.attendant.presenter.EditProfilePresenter;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;

public class EditProfileActivity extends BaseActivity implements
        IAttendantProfileContract.IAttendantProfileView{

    private EditProfilePresenter mEditProfilePresenter;

    @BindView(R.id.tv_attendant_email)
    TextView tvAttendantEmail;
    @BindView(R.id.tv_attendant_name)
    TextView tvAttendantName;

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

    @BindView(R.id.et_ref_point_description)
    EditText etRefPointDescription;
    @BindView(R.id.et_ref_point_address)
    EditText etRefPointAddress;
    @BindView(R.id.et_ref_point_lat)
    EditText etRefPointLat;
    @BindView(R.id.et_ref_point_lng)
    EditText etRefPointLng;

    @BindView(R.id.btn_get_coords_current_position)
    Button btnGetCoordsCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);

        mEditProfilePresenter = new EditProfilePresenter(EditProfileActivity.this);
        etDocIdType.requestFocus();

    }

    @OnClick(R.id.btn_save)
    public void save(){
        saveAttendant();
    }


    @OnClick(R.id.btn_get_coords_current_position)
    public void getCoordsCurrentPosition(){
        showProgressBar(true);
        mEditProfilePresenter.getCoordsCurrentPosition();
    }

    private void saveAttendant() {
        AttendantBO attendantBO = new AttendantBO();
        attendantBO.setDocId(etDocIdType.getText() != null ? etDocIdType.getText().toString() : "");
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
        RefPointBO refPointBO = new RefPointBO();
        refPointBO.setDescription(etRefPointDescription.getText() != null ? etRefPointDescription.getText().toString() : "");
        refPointBO.setAddress(etRefPointAddress.getText() != null ? etRefPointAddress.getText().toString() : "");
        refPointBO.setLat(etRefPointLat.getText() != null && !etRefPointLat.getText().toString().isEmpty() ? Double.parseDouble(etRefPointLat.getText().toString()) : 0);
        refPointBO.setLng(etRefPointLng.getText() != null && !etRefPointLng.getText().toString().isEmpty() ? Double.parseDouble(etRefPointLng.getText().toString()) : 0);
        attendantBO.setRefPoint(refPointBO);
        mEditProfilePresenter.saveAttendant(attendantBO);
    }

    void enableFields(boolean enable){
        enableFieldsPersonalInfo(enable);
        enableFieldsContactInfo(enable);
        enableFieldsRefPoint(enable);
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

    void enableFieldsRefPoint (boolean enable){
        etRefPointDescription.setEnabled(enable);
        etRefPointAddress.setEnabled(enable);
        etRefPointLat.setEnabled(enable);
        etRefPointLng.setEnabled(enable);
    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void setAttendantUI(AttendantBO attendantBO) {
        tvAttendantEmail.setText((attendantBO.getEmail() != null) ? attendantBO.getEmail() : "");
        tvAttendantName.setText((attendantBO.getFirstName() != null) ? attendantBO.getFirstName() : "");
        etDocIdType.setText((attendantBO.getDocIdType() != null) ? attendantBO.getDocIdType().toString() : "");
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
        if(attendantBO.getRefPoint() != null ){
            etRefPointDescription.setText((attendantBO.getRefPoint().getDescription() != null) ? attendantBO.getRefPoint().getDescription() : "");
            etRefPointAddress.setText((attendantBO.getRefPoint().getAddress() != null) ? attendantBO.getRefPoint().getAddress() : "");
            etRefPointLat.setText(( attendantBO.getRefPoint().getLat() != 0) ? String.valueOf(attendantBO.getRefPoint().getLat()) : "");
            etRefPointLng.setText(( attendantBO.getRefPoint().getLng() != 0) ? String.valueOf(attendantBO.getRefPoint().getLng()) : "");
        } else {
            etRefPointDescription.setText("");
            etRefPointAddress.setText("");
            etRefPointLat.setText("");
            etRefPointLng.setText("");
        }

    }

    @Override
    public void setCoordsCurrentPositionUI(Location location) {
        etRefPointLat.setText(String.valueOf(location.getLatitude()));
        etRefPointLng.setText(String.valueOf(location.getLongitude()));
        showProgressBar(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mEditProfilePresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mEditProfilePresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEditProfilePresenter.onDestroy();
    }
}
