package co.jestrada.cupoescolarapp.common;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.jestrada.cupoescolarapp.login.interactor.LoginInteractor;
import co.jestrada.cupoescolarapp.login.model.User;
import co.jestrada.cupoescolarapp.login.presenter.LoginPresenter;
import co.jestrada.cupoescolarapp.login.presenter.SignUpPresenter;
import co.jestrada.cupoescolarapp.login.view.LoginActivity;

public class AppCore extends Application {

}
