package co.jestrada.cupoescolarapp.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
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

    public void goToEnrolledStudents(){
/*        Intent intent = new Intent(this, EnrolledStudentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
    }

    @OnClick(R.id.btn_sign_in_email_password)
    public void signInEmailPassword(){
        mLoginPresenter.signInEmailPassword(etEmail, etPassword);
    }

    @OnClick(R.id.tv_forget_password)
    public void forgetPassword(){
        goToForgetPassword();
    }

    private void goToForgetPassword() {
/*        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);*/
    }

    @OnClick(R.id.tv_create_email_account)
    public void signUpEmailPassword(){
        goToSignUpEmailPassword();
    }

    private void goToSignUpEmailPassword() {
        Intent intent = new Intent(this, SignUpActivity.class);
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

    private String getErrMessage(String etName) {
        String errEditText;
        switch (etName){
            case Fields.PASSWORD:
                errEditText = getString(R.string.validate_input_password);
                break;
            case Fields.EMAIL:
                errEditText = getString(R.string.validate_input_email);
                break;
            default:
                errEditText = getString(R.string.validate_input_unidentify);
                break;
        }
        return errEditText;
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Error del login", Toast.LENGTH_SHORT).show();
    }
}
