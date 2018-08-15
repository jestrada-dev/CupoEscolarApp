package co.jestrada.cupoescolarapp.location.contract;

import co.jestrada.cupoescolarapp.location.model.bo.RefPositionBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface IRefPositionContract extends IBaseContract {

    interface IRefPositionView{

        void getRefPositionTransactionState(boolean successful);
        void setRefPositionUI(RefPositionBO refPositionBO, boolean isChanged);
    }

    interface IRefPositionPresenter{

        void getRefPositionTransactionState(boolean successful);
        void getData();

        void getRefPosition(RefPositionBO refPositionBO, boolean isChanged);
        void saveRefPosition(RefPositionBO refPositionBO);
        void saveDescriptionRefPosition(RefPositionBO refPositionBO);
    }

}
