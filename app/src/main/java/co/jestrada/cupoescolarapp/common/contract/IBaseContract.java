package co.jestrada.cupoescolarapp.common.contract;

import android.app.Activity;
import android.content.Context;

public interface IBaseContract {

    interface IBaseView {

        String getErrInputMessage(String etName);
        void showProgressBar(boolean visible);

    }

    interface IBasePresenter{

    }

    interface IBaseInteractor{

    }

}
