package co.jestrada.cupoescolarapp.login.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;
import co.jestrada.cupoescolarapp.login.contract.ISignUpContract;
import co.jestrada.cupoescolarapp.login.presenter.SignUpPresenter;

public class SignUpActivity extends BaseActivity implements
        ISignUpContract.ISignUpView, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.et_email)
    EditText etEmail;
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

        ButterKnife.bind(this);

        mSignUpPresenter = new SignUpPresenter(SignUpActivity.this);

        mBuilder = new AlertDialog.Builder(this);

    }

    @Override
    public void showNeutralDialog(String title, String message, String textNeutralButton) {
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
    public void goToMain() {
        Intent intent = new Intent();
        startActivity(intent);
        finish();
    }

    private void enableFields(boolean enable) {
        etEmail.setEnabled(enable);
        etPassword.setEnabled(enable);
        btnSignUp.setEnabled(enable);
    }

    @OnClick(R.id.btn_sign_up_email_password)
    public void signUpEmailPassword(){
        enableFields(false);
        showProgressBar(true);
        mSignUpPresenter.signUpEmailPassword(etEmail, etPassword);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void showErrorValidateEditText(EditText editText, String etName) {
        showProgressBar(false);
        editText.setError(getErrInputMessage(etName));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
