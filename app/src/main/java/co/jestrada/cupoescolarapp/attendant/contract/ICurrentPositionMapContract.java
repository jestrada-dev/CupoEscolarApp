package co.jestrada.cupoescolarapp.attendant.contract;

import android.location.Location;

import java.util.ArrayList;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.DocIdTypeBO;
import co.jestrada.cupoescolarapp.attendant.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface ICurrentPositionMapContract extends IBaseContract {

    interface ICurrentPositionMapView{
        void setRefPosition(RefPositionBO refPositionBO);
        void setCurrentPositionMap(double lat, double lng);
        void onStart();
        void onStop();
        void onDestroy();
    }

    interface ICurrentPositionMapPresenter{

        void getRefPosition(RefPositionBO refPositionBO);
        void saveRefPosition(RefPositionBO refPositionBO);

        void onStart();
        void onStop();
        void onDestroy();
    }
}
