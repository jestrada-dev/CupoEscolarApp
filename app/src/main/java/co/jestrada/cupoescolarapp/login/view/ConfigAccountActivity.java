package co.jestrada.cupoescolarapp.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;

public class ConfigAccountActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_account);

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

    }

    private void enableInputs(boolean enable) {

    }
}
