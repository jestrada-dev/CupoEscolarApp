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
    public String getErrInputMessage(String etName) {
        String errEditText;
        switch (etName){
            case Fields.PASSWORD:
                errEditText = getString(R.string.validate_input_password);
                break;
            case Fields.EMAIL:
                errEditText = getString(R.string.validate_input_email);
                break;
            case Fields.LAST_NAME:
                errEditText = getString(R.string.validate_input_last_name);
                break;
            case Fields.FIRST_NAME:
                errEditText = getString(R.string.validate_input_first_name);
                break;
            default:
                errEditText = getString(R.string.validate_input_unidentify);
                break;
        }
        return errEditText;
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
