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
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;
import co.jestrada.cupoescolarapp.attendant.contract.IMainContract;
import co.jestrada.cupoescolarapp.attendant.presenter.MainPresenter;
import co.jestrada.cupoescolarapp.login.view.ConfigAccountActivity;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;
import co.jestrada.cupoescolarapp.social.SendSuggestionsActivity;
import co.jestrada.cupoescolarapp.social.SocialActivity;
import co.jestrada.cupoescolarapp.student.StudentsActivity;

public class MainActivity extends BaseActivity implements
        IMainContract.IMainView{

    private BottomNavigationView mBottomNavigationView;
    private View leftNavViewHeader;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;

    private Toolbar mToolbar;

    private EnrollsStudentsFragment mEnrollsStudentsFragment;
    private DashboardFragment mDashboardFragment;
    private NotificationsFragment mNotificationsFragment;
    private ClosestSchoolsFragment mClosestSchoolsFragment;

    private MainPresenter mMainPresenter;

    private TextView tvAttendantName;
    private TextView tvAttendantEmail;

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
        mClosestSchoolsFragment = new ClosestSchoolsFragment();

        mNavigationView = (NavigationView) findViewById(R.id.left_nav_view);
        leftNavViewHeader = mNavigationView.getHeaderView(0);
        tvAttendantName = (TextView) leftNavViewHeader.findViewById(R.id.tv_user_name);
        tvAttendantEmail = (TextView) leftNavViewHeader.findViewById(R.id.tv_user_email);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.menu_item_edit_profile:
                        goToEditProfile();
                        return true;

                    case R.id.menu_item_ref_position:
                        goToRefPosition();
                        return true;

                    case R.id.menu_item_students:
                        goToStudents();
                        return true;

                    case R.id.menu_item_share_app:
                        goToShareApp();
                        return true;

                    case R.id.menu_item_send_suggestions:
                        goToSendSuggestions();
                        return true;

                    case R.id.menu_item_config_account:
                        goToConfigAccount();
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
                        setTitleToolbar(getString(R.string.enroll_students));
                        return true;

                    case R.id.menu_nav_dashboard:
                        setFragment(mDashboardFragment);
                        setTitleToolbar(getString(R.string.dashboard));
                        return true;

                    case R.id.menu_nav_notifications:
                        setFragment(mNotificationsFragment);
                        setTitleToolbar(getString(R.string.notifications));
                        return true;

                    case R.id.menu_nav_search_schools:
                        setFragment(mClosestSchoolsFragment);
                        setTitleToolbar(getString(R.string.closest_schools));
                        return true;

                    default:
                        return false;
                }

            }
        });

        mBottomNavigationView.setSelectedItemId(R.id.menu_nav_dashboard);
        setFragment(mDashboardFragment);
        setTitleToolbar(getString(R.string.dashboard));
    }

    private void goToRefPosition() {
        Intent intent = new Intent(MainActivity.this, RefPositionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToConfigAccount() {
        Intent intent = new Intent(MainActivity.this, ConfigAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToSendSuggestions() {
        Intent intent = new Intent(MainActivity.this, SendSuggestionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToShareApp() {
        Intent intent = new Intent(MainActivity.this, SocialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToEditProfile() {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToStudents() {
        Intent intent = new Intent(MainActivity.this, StudentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_avatar_bold_48);
        }
    }


    private void setTitleToolbar(String title){
        getSupportActionBar().setTitle(title);
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
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivity(intent);
    }

    @Override
    public void setNavViewUI(AttendantBO attendantBO) {
        tvAttendantName.setText((attendantBO.getFirstName() != null) ? attendantBO.getFirstName() : "");
        tvAttendantEmail.setText((attendantBO.getEmail() != null) ? attendantBO.getEmail() : "");
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
