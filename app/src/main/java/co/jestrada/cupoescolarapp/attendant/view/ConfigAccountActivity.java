package co.jestrada.cupoescolarapp.attendant.view;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.attendant.constant.ConstantsAccount;
import co.jestrada.cupoescolarapp.attendant.contract.IConfigAccountContract;
import co.jestrada.cupoescolarapp.attendant.presenter.ConfigAccountPresenter;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

public class ConfigAccountActivity extends BaseActivity implements IConfigAccountContract.IConfigAccountView{

    private Toolbar mToolbar;

    @BindView(R.id.tv_attendant_name)
    TextView tvAttendantName;
    @BindView(R.id.tv_attendant_email)
    TextView tvAttendantEmail;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_change_email)
    Button btnChangeEmail;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_change_password)
    Button btnChangePassword;

    @BindView(R.id.tv_close_account)
    TextView tvCloseAccount;

    private ConfigAccountPresenter mConfigAccountPresenter;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_account);

        mConfigAccountPresenter = new ConfigAccountPresenter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

        getData();
    }

    private void initView() {
        ButterKnife.bind(this);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                tilEmail.setError("");
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                tilPassword.setError("");
            }
        });

        setToolbar();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.config_account);
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
        setAttendantUI();
    }

    private void enableInputs(boolean enable) {
        etEmail.setEnabled(enable);
        btnChangeEmail.setEnabled(enable);
        etPassword.setEnabled(enable);
        btnChangePassword.setEnabled(enable);
        tvCloseAccount.setEnabled(enable);
    }

    @OnClick(R.id.btn_change_email)
    public void changeEmail (){
        showDialogNewEmail();
    }

    @OnClick(R.id.btn_change_password)
    public void changePassword (){
        showDialogNewPassword();
    }

    private void showDialogNewEmail() {
        if(!isValidEmail(etEmail.getText().toString().trim())){
            tilEmail.setError(getString(R.string.validate_input_email));
            etEmail.requestFocus();
        } else {
            showProgressBar(true);
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setCancelable(false);
            mBuilder.setTitle(getString(R.string.change_email_account));
            mBuilder.setMessage(R.string.enter_password);
            final EditText etPass = new EditText(this);
            etPass.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            etPass.setLayoutParams(layoutParams);
            mBuilder.setView(etPass);
            mBuilder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String password = etPass.getText().toString();
                    mConfigAccountPresenter.validateCredentials(password, ConstantsAccount.CHANGE_EMAIL);
                }
            });
            mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getData();
                }
            });
            mBuilder.show();

        }
    }

    private boolean isValidEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isValidPassword(String password){
        return password.length() >= 8;
    }

    private void showDialogNewPassword() {
        if(!isValidPassword(etPassword.getText().toString())){
            tilPassword.setError(getString(R.string.validate_input_password));
            etPassword.requestFocus();
        } else {
            showProgressBar(true);
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setCancelable(false);
            mBuilder.setTitle(getString(R.string.change_password));
            mBuilder.setMessage(getString(R.string.enter_password));
            final EditText etOldPassword = new EditText(this);
            etOldPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            etOldPassword.setLayoutParams(layoutParams);
            mBuilder.setView(etOldPassword);
            mBuilder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String password = etOldPassword.getText().toString();
                    mConfigAccountPresenter.validateCredentials(password, ConstantsAccount.CHANGE_PASSWORD);
                }
            });
            mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            mBuilder.show();

        }
    }

    @Override
    public void setAttendantUI() {
        showProgressBar(false);
        enableInputs(true);
        AttendantBO attendantBO = AttendantBO.getInstance();
        tvAttendantName.setText((attendantBO.getFirstName() != null) ? attendantBO.getFirstName() + " " + attendantBO.getLastName() : "");
        tvAttendantEmail.setText((attendantBO.getEmail() != null) ? attendantBO.getEmail() : "");
        etEmail.setText((attendantBO.getEmail() != null) ? attendantBO.getEmail() : "");
    }

    @Override
    public void validateCredentials(boolean isValidCredentials, String emailOrPassword) {
        if (emailOrPassword.equals(ConstantsAccount.CHANGE_EMAIL)){
            if (isValidCredentials){
                String email = etEmail.getText().toString().trim();
                mConfigAccountPresenter.changeEmailAccount(email);
            } else {
                showProgressBar(false);
                showDialogInvalidCredentials();
            }
        } else {
            if (isValidCredentials){
                String password = etPassword.getText().toString();
                mConfigAccountPresenter.changePassword(password);
            }  else {
                showProgressBar(false);

            }
        }

    }

    private void showDialogInvalidCredentials() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.password_incorrect);
        mBuilder.setMessage(R.string.password_incorrect_try_again);
        final EditText etOldPassword = new EditText(this);
        etOldPassword.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        etOldPassword.setLayoutParams(layoutParams);
        mBuilder.setView(etOldPassword);
        mBuilder.setNegativeButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.show();
    }

    @Override
    public void changePassword(boolean isSuccessful) {
        if (isSuccessful){
            etPassword.setText("");
            showProgressBar(false);
            showDialogPasswordChanged();
        }

    }

    private void showDialogPasswordChanged() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.password_changed_successfully);
        mBuilder.setMessage(R.string.password_changed_succesfully_summary);
        mBuilder.setNeutralButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.show();
    }

    @Override
    public void showDialogSendVerifyEmail() {
        showProgressBar(false);
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.confirm_new_email);
        mBuilder.setMessage(R.string.new_email_registered_successfully);
        mBuilder.setNeutralButton(R.string.check_my_email, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        mBuilder.show();
    }
}
