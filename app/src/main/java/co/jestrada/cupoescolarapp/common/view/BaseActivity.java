package co.jestrada.cupoescolarapp.common.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import co.jestrada.cupoescolarapp.R;
import co.jestrada.cupoescolarapp.common.constant.Fields;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public class BaseActivity extends AppCompatActivity
        implements IBaseContract.IBaseView{

    @BindView(R.id.pb)
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgressBar(boolean visible) {
        if (visible){
            pb.setVisibility(View.VISIBLE);
        }else {
            pb.setVisibility(View.GONE);
        }
    }


}
