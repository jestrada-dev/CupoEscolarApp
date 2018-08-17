package co.jestrada.cupoescolarapp.login.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.login.constants.ConstantsAccount;
import co.jestrada.cupoescolarapp.login.contract.IConfigAccountContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.login.presenter.ConfigAccountPresenter;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

public class ConfigAccountActivity extends BaseActivity implements IConfigAccountContract.IConfigAccountView{

    private Toolbar mToolbar;

    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_change_email)
    Button btnChangeEmail;

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
        mConfigAccountPresenter.getData();
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
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Cambiar tu Cuenta de Correo");
        mBuilder.setMessage("Escribe tu contraseña para validar credenciales:");
        final EditText etPass = new EditText(this);
        etPass.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        etPass.setLayoutParams(layoutParams);
        mBuilder.setView(etPass);
        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String password = etPass.getText().toString();
                mConfigAccountPresenter.validateCredentials(password, ConstantsAccount.CHANGE_EMAIL);
            }
        });
        mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getData();
            }
        });
        mBuilder.show();
    }

    private void showDialogNewPassword() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Cambiar tu Contraseña");
        mBuilder.setMessage("Escribe tu contraseña actual para validar credenciales:");
        final EditText etPass = new EditText(this);
        etPass.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        etPass.setLayoutParams(layoutParams);
        mBuilder.setView(etPass);
        mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String password = etPass.getText().toString();
                mConfigAccountPresenter.validateCredentials(password, ConstantsAccount.CHANGE_PASSWORD);
            }
        });
        mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getData();
            }
        });
        mBuilder.show();
    }


    @Override
    public void getUserTransactionState(boolean successful) {

    }

    @Override
    public void setUserUI(UserBO userBO, boolean isChanged) {
        showProgressBar(false);
        enableInputs(true);
        tvUserEmail.setText((userBO.getEmail() != null) ? userBO.getEmail() : "");
        etEmail.setText((userBO.getEmail() != null) ? userBO.getEmail() : "");
    }

    @Override
    public void validateCredentials(boolean isValidCredentials) {
        showDialogSendVerifyEmail();
    }

    @Override
    public void changeEmail(boolean isSuccessful) {

    }

    @Override
    public void changePassword(boolean isSuccessful) {
        showDialogPasswordChanged();
    }

    private void showDialogPasswordChanged() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Contraseña cambiada exitosamente");
        mBuilder.setMessage("Tu nueva contraseña se ha cambiado exitosamente.");
        mBuilder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.show();
    }

    @Override
    public void showDialogSendVerifyEmail() {
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Confirma tu Nueva cuenta de correo");
        mBuilder.setMessage("Hemos registrado tu nueva cuenta exitosamente! El siguiente paso es confirmar el correo electrónico. Hemos enviado un mail de confirmación a tu buzón.");
        mBuilder.setNeutralButton("OK! Voy a revisar mi correo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.show();
    }
}
