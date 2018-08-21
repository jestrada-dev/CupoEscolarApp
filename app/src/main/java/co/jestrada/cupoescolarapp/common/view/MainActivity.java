package co.jestrada.cupoescolarapp.common.view;

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
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.view.EditProfileActivity;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;
import co.jestrada.cupoescolarapp.common.contract.IMainContract;
import co.jestrada.cupoescolarapp.common.presenter.MainPresenter;
import co.jestrada.cupoescolarapp.location.view.RefPositionActivity;
import co.jestrada.cupoescolarapp.attendant.view.ConfigAccountActivity;
import co.jestrada.cupoescolarapp.attendant.view.LoginActivity;
import co.jestrada.cupoescolarapp.social.view.SendSuggestionsActivity;
import co.jestrada.cupoescolarapp.social.view.SocialActivity;
import co.jestrada.cupoescolarapp.student.view.StudentsActivity;

public class MainActivity extends BaseActivity implements
        IMainContract.IMainView{

    BottomNavigationView mBottomNavigationView;
    View leftNavViewHeader;
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    //FrameLayout mFrameLayout;

    private Toolbar mToolbar;

    private EnrollsStudentsFragment mEnrollsStudentsFragment;
    private DashboardFragment mDashboardFragment;
    private ListSchoolFragment mListSchoolFragment;
    private MapSchoolsFragment mMapSchoolsFragment;

    private MainPresenter mMainPresenter;

    private TextView tvAttendantEmail;
    private TextView tvAttendantName;

    private boolean closeOnBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainPresenter = new MainPresenter(MainActivity.this);

        mEnrollsStudentsFragment = new EnrollsStudentsFragment();
        mDashboardFragment = new DashboardFragment();
        mListSchoolFragment = new ListSchoolFragment();
        mMapSchoolsFragment = new MapSchoolsFragment();

        initView();

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.left_nav_view);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        leftNavViewHeader = mNavigationView.getHeaderView(0);
        tvAttendantEmail = (TextView) leftNavViewHeader.findViewById(R.id.tv_attendant_email);
        tvAttendantName = (TextView) leftNavViewHeader.findViewById(R.id.tv_attendant_name);

        setToolbar();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return goToViewOnLeftNavItemSelected(menuItem.getItemId());
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        return setFragmentOnNavItemSelected(menuItem.getItemId());
                    }
                });

        mBottomNavigationView.setSelectedItemId(R.id.menu_nav_dashboard);

        setFragment(mDashboardFragment);
        setTitleToolbar(getString(R.string.dashboard));
        mMainPresenter.getData();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_avatar_bold_48);
        }
    }

    private void setTitleToolbar(String title){
        getSupportActionBar().setTitle(title);
    }

    private boolean goToViewOnLeftNavItemSelected(int leftNavMenuItemId) {

        switch (leftNavMenuItemId){
            case R.id.menu_item_edit_profile:
                goToEditProfile();
                break;

            case R.id.menu_item_ref_position:
                goToRefPosition();
                break;

            case R.id.menu_item_students:
                goToStudents();
                break;

            case R.id.menu_item_share_app:
                goToShareApp();
                break;

            case R.id.menu_item_send_suggestions:
                goToSendSuggestions();
                break;

            case R.id.menu_item_config_account:
                goToConfigAccount();
                break;

            case R.id.menu_item_sign_out:
                signOut();
                break;

            default:
                return false;
        }
        return true;
    }

    private boolean setFragmentOnNavItemSelected(int bottomNavMenuItem) {
        switch (bottomNavMenuItem){
            case R.id.menu_nav_enrolls:
                setFragment(mEnrollsStudentsFragment);
                setTitleToolbar(getString(R.string.enroll_students));
                break;

            case R.id.menu_nav_dashboard:
                setFragment(mDashboardFragment);
                setTitleToolbar(getString(R.string.dashboard));
                break;

            case R.id.menu_nav_list_schools_closest:
                setFragment(mListSchoolFragment);
                setTitleToolbar(getString(R.string.list_closest_schools));
                break;

            case R.id.menu_nav_map_school_closest:
                setFragment(mMapSchoolsFragment);
                setTitleToolbar(getString(R.string.map_closest_schools));
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public void setNavViewUI(boolean isChanged) {
        if(isChanged){
            AttendantBO attendantBO = AttendantBO.getInstance();
            tvAttendantName.setText((attendantBO.getUserUid() != null) ? attendantBO.getUserUid() : "");
            tvAttendantEmail.setText((attendantBO.getEmail() != null) ? attendantBO.getEmail() : "");
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

    private void setFragment(Fragment fragment){
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        mFragmentTransaction.replace(R.id.fl_main, fragment);
        mFragmentTransaction.commit();
        mFragmentTransaction.addToBackStack(null);
    }

    @Override
    public void getRefPositionTransactionState(boolean successful) {
    }

    @Override
    public void getAttendantTransactionState(boolean successful) {
        if(successful){

        }
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivity(intent);
    }

    public void signOut(){
        mMainPresenter.signOut();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!closeOnBackPressed){
            closeOnBackPressed = true;
            Toast.makeText(this, R.string.try_again_to_close, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeOnBackPressed = false;
    }

    private void goToRefPosition() {
        Intent intent = new Intent(this, RefPositionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToConfigAccount() {
        Intent intent = new Intent(this, ConfigAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToSendSuggestions() {
        Intent intent = new Intent(this, SendSuggestionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToShareApp() {
        Intent intent = new Intent(this, SocialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToEditProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void goToStudents() {
        Intent intent = new Intent(this, StudentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
