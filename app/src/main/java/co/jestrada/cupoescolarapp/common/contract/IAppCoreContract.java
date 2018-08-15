package co.jestrada.cupoescolarapp.common.contract;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;
import co.jestrada.cupoescolarapp.base.contract.IBaseContract;

public interface IAppCoreContract extends IBaseContract {

    interface IAppCore extends IBaseContract.IBaseInteractor{
        void getAttendant(AttendantBO attendantBO, boolean isChanged);
    }
}
