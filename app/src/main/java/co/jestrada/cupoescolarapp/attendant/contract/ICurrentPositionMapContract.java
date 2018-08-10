package co.jestrada.cupoescolarapp.attendant.contract;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface ICurrentPositionMapContract extends IBaseContract {

    interface ICurrentPositionMapView{

    }

    interface ICurrentPositionMapPresenter{

        void getRefPosition(RefPositionBO refPositionBO);
        void saveRefPosition(RefPositionBO refPositionBO);

        void onStart();
        void onStop();
        void onDestroy();
    }
}
