package co.jestrada.cupoescolarapp.location.contract;

import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface ICurrentPositionMapContract extends IBaseContract {

    interface ICurrentPositionMapView{
        void getRefPositionTransactionState(boolean successful);

        void setRefPosition(RefPositionBO refPositionBO, boolean isChanged);
        void setCurrentPositionMap(double lat, double lng);
        void onStart();
        void onStop();
        void onDestroy();
    }

    interface ICurrentPositionMapPresenter{

        void getRefPositionTransactionState(boolean successful);

        void getRefPosition(RefPositionBO refPositionBO, boolean isChanged);
        void saveRefPosition(RefPositionBO refPositionBO);
        void saveRefPositionNoDescription(RefPositionBO refPositionBO);

        void onStart();
        void onStop();
        void onDestroy();
    }
}
