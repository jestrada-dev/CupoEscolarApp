package co.jestrada.cupoescolarapp.login.contract;

import co.jestrada.cupoescolarapp.common.contract.IBaseContract;

public interface IMainContract extends IBaseContract {

    interface IMainView{
        void goToLogin();
    }

    interface IMainPresenter{
        void signOut();

        void onStart();
        void onStop();
        void onDestroy();
    }

}
