package co.jestrada.cupoescolarapp.attendant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.login.model.bo.UserBO;
import co.jestrada.cupoescolarapp.attendant.presenter.MainPresenter;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;

public class MainActivity extends BaseActivity implements
        IMainContract.IMainView{

//    private BottomNavigationView mBottomNavigationView;

    private FrameLayout mFrameLayout;

    private android.support.v7.widget.Toolbar mToolbar;

    private DashboardFragment mDashboardFragment;
    private SearchSchoolsFragment mSearchSchoolsFragment;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(MainActivity.this);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        if(mToolbar != null){
            setSupportActionBar(mToolbar);
        }

//        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);

        mDashboardFragment = new DashboardFragment();
        mSearchSchoolsFragment = new SearchSchoolsFragment();

        setFragment(mDashboardFragment);

/*
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_nav_menu:

                        return true;

                    case R.id.menu_nav_dashboard:
                        setFragment(mDashboardFragment);
                        return true;

                    case R.id.menu_nav_search_schools:
                        setFragment(mSearchSchoolsFragment);
                        return true;

                    default:
                        return false;
                }

            }
        });
*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        mFragmentTransaction.replace(R.id.fl_main, fragment);
        mFragmentTransaction.commit();
    }

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
    public void setUIToolbar() {
        if(mToolbar != null){
            UserBO userBOApp = UserBO.getInstance();
            mToolbar.setTitle(userBOApp.getEmail());
            mToolbar.setSubtitle(userBOApp.getFirstName());
            mToolbar.setLogo(getDrawable(R.drawable.ic_account_circle));
        }
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
