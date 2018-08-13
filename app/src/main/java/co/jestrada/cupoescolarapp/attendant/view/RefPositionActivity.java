package co.jestrada.cupoescolarapp.attendant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.attendant.presenter.RefPositionPresenter;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;

public class RefPositionActivity extends BaseActivity implements
        IRefPositionContract.IRefPositionView{

    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_lat)
    EditText etLat;
    @BindView(R.id.et_lng)
    EditText etLng;
    @BindView(R.id.et_postal_code)
    EditText etPostalCode;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.btn_get_current_position)
    Button btnGetCurrentPosition;

    private RefPositionPresenter mRefPositionPresenter;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_position);

        ButterKnife.bind(this);

        mRefPositionPresenter = new RefPositionPresenter(RefPositionActivity.this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar();
    }

    private void setToolbar() {
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.set_current_position);
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_48);
            //TODO Establecer el botón de atrás
        }
    }

    @OnClick(R.id.btn_save)
    public void saveDescription(){
        showProgressBar(true);
        enableInputs(false);
        RefPositionBO refPositionBO;
        refPositionBO = new RefPositionBO();
        refPositionBO.setDescription(etDescription.getText().toString());
        mRefPositionPresenter.saveDescriptionRefPosition(refPositionBO);
    }

    private void enableInputs(boolean enable) {
        etDescription.setEnabled(enable);
    }

    @OnClick(R.id.btn_get_current_position)
    public void getCurrentPosition(){
        goToCurrentPositionMap();
    }

    private void goToCurrentPositionMap() {
        Intent intent = new Intent(RefPositionActivity.this, CurrentPositionMapActivity.class);
        startActivity(intent);
    }


    @Override
    public void setRefPositionUI(RefPositionBO refPositionBO, boolean isChanged) {
        if(isChanged){
            etDescription.setText((refPositionBO.getDescription() != null) ? refPositionBO.getDescription() : "");
            etAddress.setText((refPositionBO.getAddress() != null) ? refPositionBO.getAddress() : "");
            etLat.setText((refPositionBO.getLat() != 0) ? String.valueOf(refPositionBO.getLat()) : "");
            etLng.setText((refPositionBO.getLng() != 0) ? String.valueOf(refPositionBO.getLng()) : "");
            etPostalCode.setText((refPositionBO.getPostalCode() != null) ? refPositionBO.getPostalCode() : "");
            etCity.setText((refPositionBO.getCity() != null) ? refPositionBO.getCity() : "");
            Toast.makeText(this, R.string.ref_positions_updated,Toast.LENGTH_LONG).show();
        }
        showProgressBar(false);
        enableInputs(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressBar(true);
        mRefPositionPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRefPositionPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRefPositionPresenter.onDestroy();
    }
}
