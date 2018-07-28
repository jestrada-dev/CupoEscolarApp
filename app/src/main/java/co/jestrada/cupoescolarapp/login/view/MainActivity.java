package co.jestrada.cupoescolarapp.login.view;

import android.os.Bundle;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;
import co.jestrada.cupoescolarapp.login.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.presenter.LoginPresenter;
import co.jestrada.cupoescolarapp.login.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements
        IMainContract.IMainView{

    @BindView(R.id.btn_sign_out)
    Button btnSignOut;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(MainActivity.this);

    }

    @OnClick(R.id.btn_sign_out)
    public void signOut(){
        mMainPresenter.signOut();
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }
}
