package co.jestrada.cupoescolarapp.location.view;

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
import co.jestrada.cupoescolarapp.location.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.location.presenter.RefPositionPresenter;
import co.jestrada.cupoescolarapp.base.view.BaseActivity;

public class RefPositionActivity extends BaseActivity implements
        IRefPositionContract.IRefPositionView{

    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.btn_save)
    Button btnSave;

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
    @BindView(R.id.btn_get_current_position)
    Button btnGetCurrentPosition;

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
            mToolbar.setTitleTextColor(getColor(R.color.mColorNavText));
            mToolbar.setNavigationIcon(R.drawable.ic_back_bold_48);
            //TODO Establecer el botón de atrás
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

    @OnClick(R.id.btn_save)
    public void saveDescription(){
        showProgressBar(true);
        enableInputs(false);
        RefPositionBO refPositionBO = new RefPositionBO();
        refPositionBO.setDescription(etDescription.getText().toString());
        mRefPositionPresenter.saveDescriptionRefPosition(refPositionBO);
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
    public void getRefPositionTransactionState(boolean successful) {
        if (successful){
            Toast.makeText(this, R.string.ref_positions_updated_succesfully,Toast.LENGTH_LONG).show();
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
        enableInputs(true);
    }

}
