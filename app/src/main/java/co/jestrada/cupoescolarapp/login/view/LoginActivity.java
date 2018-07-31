package co.jestrada.cupoescolarapp.login.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.view.MainActivity;
import co.jestrada.cupoescolarapp.common.constant.Fields;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;
import co.jestrada.cupoescolarapp.login.contract.ILoginContract;
import co.jestrada.cupoescolarapp.login.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements
ILoginContract.ILoginView{

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_sign_in_email_password)
    Button btnSignInEmailPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;

    @BindView(R.id.tv_create_email_account)
    TextView tvCreateEmailAccount;

    @BindView(R.id.btn_sign_in_google)
    Button btnSignInGoogle;
    @BindView(R.id.btn_sign_in_facebook)
    Button btnSignInFacebook;

    private LoginPresenter mLoginPresenter;

    private AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                etEmail.setError(null, null);
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
                etPassword.setError(null, null);
            }
        });
    }


    @Override
    public void enableFields(boolean enable){
        etEmail.setEnabled(enable);
        etPassword.setEnabled(enable);
        btnSignInEmailPassword.setEnabled(enable);
        tvForgetPassword.setEnabled(enable);
        tvCreateEmailAccount.setEnabled(enable);
        btnSignInGoogle.setEnabled(enable);
        btnSignInFacebook.setEnabled(enable);
    }

    @OnClick(R.id.btn_sign_in_email_password)
    public void signInEmailPassword(){
        enableFields(false);
        showProgressBar(true);
        mLoginPresenter.signInEmailPassword(etEmail, etPassword);
    }

    @OnClick(R.id.tv_forget_password)
    public void forgetPassword(){
        mLoginPresenter.forgetMyPassword(etEmail);
    }

    @OnClick(R.id.tv_create_email_account)
    public void signUpEmailPassword(){
        goToSignUpEmailPassword();
    }

    private void goToSignUpEmailPassword() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        //finish();
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_in_google)
    public void signInGoogleCredentials(){
        mLoginPresenter.signInGoogleCredentials();
    }

    @OnClick(R.id.btn_sign_in_facebook)
    public void signInFacebookCredentials(){
        mLoginPresenter.signInFacebookCredentials();
    }

    @Override
    public void showErrorValidateEditText(EditText editText, String etName) {
        editText.setError(getErrMessage(etName));
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
        finish();
        startActivity(intent);
    }

    private String getErrMessage(String etName) {
        String errEditText;
        switch (etName){
            case Fields.PASSWORD:
                errEditText = getString(R.string.validate_input_password);
                break;
            case Fields.EMAIL:
                errEditText = getString(R.string.validate_input_email);
                break;
            case Fields.FORGET_EMAIL:
                errEditText = getString(R.string.enter_email_resend_password);
                break;
            default:
                errEditText = getString(R.string.validate_input_unidentify);
                break;
        }
        return errEditText;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoginPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDestroy();
    }
}
