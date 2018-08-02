package co.jestrada.cupoescolarapp.attendant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;

    private Toolbar mToolbar;

    private EnrollsStudentsFragment mEnrollsStudentsFragment;
    private DashboardFragment mDashboardFragment;
    private NotificationsFragment mNotificationsFragment;
    private SearchSchoolsFragment mSearchSchoolsFragment;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(MainActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mEnrollsStudentsFragment = new EnrollsStudentsFragment();
        mDashboardFragment = new DashboardFragment();
        mNotificationsFragment = new NotificationsFragment();
        mSearchSchoolsFragment = new SearchSchoolsFragment();
        setFragment(mDashboardFragment);

        mNavigationView = (NavigationView) findViewById(R.id.left_nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.menu_item_students:
                        goToStudents();
                        return true;

                    case R.id.menu_item_my_profile:
                        goToMyProfile();
                        return true;

                    case R.id.menu_item_share_app:
                        goToShareApp();
                        return true;

                    case R.id.menu_item_send_suggestions:
                        goToSendSuggestions();
                        return true;

                    case R.id.menu_item_my_account:
                        goToMyAccount();
                        return true;

                    case R.id.menu_item_sign_out:
                        signOut();
                        return true;

                    default:
                        return false;
                }
            }
        });

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_nav_enrolls:
                        setFragment(mEnrollsStudentsFragment);
                        return true;

                    case R.id.menu_nav_dashboard:
                        setFragment(mDashboardFragment);
                        return true;

                    case R.id.menu_nav_notifications:
                        setFragment(mNotificationsFragment);

                        return true;

                    case R.id.menu_nav_search_schools:
                        setFragment(mSearchSchoolsFragment);
                        return true;

                    default:
                        return false;
                }

            }
        });
    }

    private void goToMyAccount() {
    }

    private void goToSendSuggestions() {
    }

    private void goToShareApp() {
    }

    private void goToMyProfile() {
    }

    private void goToStudents() {
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_avatar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_top_toolbar, menu);
        return true;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        mFragmentTransaction.replace(R.id.fl_main, fragment);
        mFragmentTransaction.commit();
        mFragmentTransaction.addToBackStack(null);
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
