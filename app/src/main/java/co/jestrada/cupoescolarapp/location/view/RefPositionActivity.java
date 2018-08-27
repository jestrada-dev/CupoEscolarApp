package co.jestrada.cupoescolarapp.location.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.location.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.location.presenter.RefPositionPresenter;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;

public class RefPositionActivity extends BaseActivity implements
        IRefPositionContract.IRefPositionView{

    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.fav_save)
    FloatingActionButton favSave;
    @BindView(R.id.fav_edit)
    FloatingActionButton favEdit;
    @BindView(R.id.fav_cancel)
    FloatingActionButton favCancel;

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_postal_code)
    EditText etPostalCode;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_admin_area)
    EditText etAdminArea;
    @BindView(R.id.et_country)
    EditText etCountry;

    @BindView(R.id.fav_map)
    FloatingActionButton favMap;

    private RefPositionPresenter mRefPositionPresenter;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_position);

        mRefPositionPresenter = new RefPositionPresenter(RefPositionActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

    }

    private void initView() {
        ButterKnife.bind(this);
        setToolbar();
        getData();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.set_current_position);
            getSupportActionBar().setSubtitle("Obtén tus coordenadas del mapa");
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_blue_48);
            mToolbar.setTitleTextAppearance(this, R.style.TextTitle);
            mToolbar.setTitleTextColor(getColor(R.color.mColorPrimaryText));
            mToolbar.setSubtitleTextAppearance(this, R.style.TextSubtitle1);
            mToolbar.setSubtitleTextColor(getColor(R.color.mColorSecondaryText));
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
        mRefPositionPresenter.getData();
    }

    private void enableInputs(boolean enable) {
        etDescription.setEnabled(enable);
    }

    @OnClick(R.id.fav_save)
    public void saveDescription(){
        showProgressBar(true);
        enableInputs(false);
        favEdit.show();
        favSave.hide();
        favCancel.hide();
        RefPositionBO refPositionBO = new RefPositionBO();
        refPositionBO.setDescription(etDescription.getText().toString());
        mRefPositionPresenter.saveDescriptionRefPosition(refPositionBO);
    }

    @OnClick(R.id.fav_map)
    public void getCurrentPosition(){
        goToCurrentPositionMap();
    }

    @OnClick(R.id.fav_edit)
    public void edit(){
        enableInputs(true);
        etDescription.requestFocus();
        favEdit.hide();
        favSave.show();
        favCancel.show();
    }

    @OnClick(R.id.fav_cancel)
    public void cancel(){
        enableInputs(false);
        favEdit.show();
        favSave.hide();
        favCancel.hide();
    }

    private void goToCurrentPositionMap() {
        Intent intent = new Intent(RefPositionActivity.this, CurrentPositionMapActivity.class);
        startActivity(intent);
    }

    @Override
    public void getRefPositionTransactionState(boolean successful) {
        showProgressBar(false);
        if (successful){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_ref_position), R.string.ref_positions_updated_succesfully, Snackbar.LENGTH_LONG)
                    .setActionTextColor(getColor(R.color.mColorPrimaryText))
                    .setAction("Action", null);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getColor(R.color.mColorPrimaryLight));
            TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    @Override
    public void setRefPositionUI(RefPositionBO refPositionBO, boolean isChanged) {
        if(isChanged){
            etDescription.setText((refPositionBO.getDescription() != null) ? refPositionBO.getDescription() : "");
            etAddress.setText((refPositionBO.getAddress() != null) ? refPositionBO.getAddress() : "");
            etAdminArea.setText((refPositionBO.getAdminArea() != null) ? refPositionBO.getAdminArea() : "");
            etCountry.setText((refPositionBO.getAddress() != null) ? refPositionBO.getCountry() : "");
            etPostalCode.setText((refPositionBO.getPostalCode() != null) ? refPositionBO.getPostalCode() : "");
            etCity.setText((refPositionBO.getCity() != null) ? refPositionBO.getCity() : "");
        }
        showProgressBar(false);
    }

}
