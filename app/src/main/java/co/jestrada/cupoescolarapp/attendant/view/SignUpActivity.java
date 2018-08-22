package co.jestrada.cupoescolarapp.attendant.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.view.MainActivity;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.attendant.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.attendant.presenter.SignUpPresenter;

public class SignUpActivity extends BaseActivity implements
        ISignUpContract.ISignUpView {

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_sign_up_email_password)
    Button btnSignUp;

    private SignUpPresenter mSignUpPresenter;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpPresenter = new SignUpPresenter(SignUpActivity.this);

        initView();

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
    }


    private boolean isValidEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isValidPassword(String password){
        return password.length() >= 8;
    }

    private boolean isValidCredentials(){
        boolean validCredentials = true;

        if(!isValidPassword(etPassword.getText().toString())){
            tilPassword.setError(getString(R.string.validate_input_password));
            etPassword.requestFocus();
            validCredentials = false;
        }
        if(!isValidEmail(etEmail.getText().toString().trim())){
            tilEmail.setError(getString(R.string.validate_input_email));
            etEmail.requestFocus();
            validCredentials = false;
        }

        if(!validCredentials){
            enableFields(true);
        }

        return validCredentials;
    }

    @Override
    public void showNotConnectionDialog() {
        showProgressBar(false);
        enableFields(true);
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(R.string.create_account);
        mBuilder.setMessage(R.string.errorOnInternetConnection);
        mBuilder.setNeutralButton(R.string.try_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.show();
    }

    @Override
    public void showVerifyEmailSentDialog(final boolean isSuccessful) {
        showProgressBar(false);
        enableFields(true);
        String message = getString(R.string.sign_up_user_succesfully);
        String neutralButton = getString(R.string.check_my_email);
        if (!isSuccessful){
            message = getString(R.string.failed_sent_verify_email);
            neutralButton = getString(R.string.try_later);
        }
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(etEmail.getText().toString().trim());
        mBuilder.setMessage(message);
        mBuilder.setNeutralButton(neutralButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isSuccessful){
                    goToLogin();
                }
            }
        });
        mBuilder.show();
    }

    @Override
    public void showNotIdentifyErrorOnSignUp() {
        showProgressBar(false);
        enableFields(true);
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(etEmail.getText().toString().trim());
        mBuilder.setMessage(R.string.sign_up_user_failed);
        mBuilder.setNeutralButton(R.string.try_later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        mBuilder.show();

    }

    @Override
    public void showUserAlreadyRegisteredDialog() {
        showProgressBar(false);
        enableFields(true);
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(etEmail.getText().toString().trim());
        mBuilder.setMessage(R.string.firebase_user_already_registered_es);
        mBuilder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToLogin();
            }
        });
        mBuilder.setNegativeButton(R.string.try_another_email_account, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        mBuilder.show();
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivity(intent);
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivity(intent);
    }

    @Override
    public void enableFields(boolean enable) {
        etEmail.setEnabled(enable);
        etPassword.setEnabled(enable);
        btnSignUp.setEnabled(enable);
    }

    @OnClick(R.id.btn_sign_up_email_password)
    public void signUpEmailPassword(){
        if (isValidCredentials()){
            enableFields(false);
            showProgressBar(true);
            mSignUpPresenter.signUpEmailPassword(etEmail.getText().toString().trim(), etPassword.getText().toString());
        }
    }

}
