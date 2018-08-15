package co.jestrada.cupoescolarapp.login.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.view.MainActivity;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements
ILoginContract.ILoginView{

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_sign_in_email_password)
    Button btnSignInEmailPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;

    @BindView(R.id.tv_create_email_account)
    TextView tvCreateEmailAccount;

    private LoginPresenter mLoginPresenter;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity","LoginActivity -> Cargando Login Activity");

        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter(LoginActivity.this);

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

        return validCredentials;
    }

    @Override
    public void enableFields(boolean enable){
        etEmail.setEnabled(enable);
        etPassword.setEnabled(enable);
        btnSignInEmailPassword.setEnabled(enable);
        tvForgetPassword.setEnabled(enable);
        tvCreateEmailAccount.setEnabled(enable);
    }

    @OnClick(R.id.btn_sign_in_email_password)
    public void signInEmailPassword(){
        if (isValidCredentials()){
            enableFields(false);
            showProgressBar(true);
            mLoginPresenter.signInEmailPassword(etEmail.getText().toString().trim(), etPassword.getText().toString());
        }
    }

    @OnClick(R.id.tv_forget_password)
    public void forgetPassword(){
        if (isValidEmail(etEmail.getText().toString().trim())){
            showProgressBar(true);
            enableFields(false);
            mLoginPresenter.forgetMyPassword(etEmail.getText().toString().trim());
        } else {
            tilPassword.setError(getString(R.string.enter_email_resend_password));
            etPassword.requestFocus();
        }
    }

    @OnClick(R.id.tv_create_email_account)
    public void signUpEmailPassword(){
        goToSignUpEmailPassword();
    }

    private void goToSignUpEmailPassword() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        Log.d("SignUpActivity","LoginActivity -> Iniciando SignUp Activity");

        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void showNeutralDialog(String title, String message, String textNeutralButton) {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(title);
        mBuilder.setMessage(message);
        mBuilder.setNeutralButton(textNeutralButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enableFields(true);
            }
        });
        mBuilder.show();
    }

    @Override
    public void showVerifyEmailDialog(String title, String message, String textPositiveButton, String textNegativeButton) {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle(title);
        mBuilder.setMessage(message);
        mBuilder.setPositiveButton(textPositiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mLoginPresenter.sendVerificationEmail();
                enableFields(true);
            }
        });
        mBuilder.setNegativeButton(textNegativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enableFields(true);
            }
        });
        mBuilder.show();
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Log.d("MainActivity","LoginActivity -> Iniciando Main Activity");

        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
