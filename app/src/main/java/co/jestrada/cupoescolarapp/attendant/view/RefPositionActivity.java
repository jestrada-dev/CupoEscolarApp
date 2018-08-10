package co.jestrada.cupoescolarapp.attendant.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.attendant.contract.IRefPositionContract;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.attendant.presenter.RefPositionPresenter;
import co.jestrada.cupoescolarapp.common.view.BaseActivity;

public class RefPositionActivity extends BaseActivity implements
        IRefPositionContract.IRefPositionView{

    private RefPositionPresenter mRefPositionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref_position);

        mRefPositionPresenter = new RefPositionPresenter(RefPositionActivity.this);
    }


    @Override
    public void setRefPositionUI(RefPositionBO refPositionBO) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
