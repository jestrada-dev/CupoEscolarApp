package co.jestrada.cupoescolarapp.attendant.contract;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface IRefPositionContract extends IBaseContract {

    interface IRefPositionView{
        void setRefPositionUI(RefPositionBO refPositionBO);
        void onStart();
        void onStop();
        void onDestroy();
    }

    interface IRefPositionPresenter{

        void getRefPosition(RefPositionBO refPositionBO);
        void saveRefPosition(RefPositionBO refPositionBO);
        void saveDescriptionRefPosition(RefPositionBO refPositionBO);

        void onStart();
        void onStop();
        void onDestroy();
    }

}
