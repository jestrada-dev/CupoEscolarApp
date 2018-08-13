package co.jestrada.cupoescolarapp.common.contract;

import android.widget.EditText;

import co.jestrada.cupoescolarapp.attendant.model.bo.AttendantBO;

public interface IAppCoreContract extends IBaseContract {

    interface IAppCore extends IBaseContract.IBaseInteractor{
        void getAttendant(AttendantBO attendantBO, boolean isChanged);
    }
}
