package co.jestrada.cupoescolarapp.login.interactor;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.jestrada.cupoescolarapp.common.constant.Firebase;
import co.jestrada.cupoescolarapp.login.model.LoginMethodEnum;
import co.jestrada.cupoescolarapp.login.model.User;
import co.jestrada.cupoescolarapp.login.model.UserModel;
import co.jestrada.cupoescolarapp.login.model.userChilds.LoginMethodModel;
import co.jestrada.cupoescolarapp.login.presenter.SignUpPresenter;

public class LoginInteractor {

    private FirebaseDatabase mFirebaseDB;

    private SignUpPresenter mSignUpPresenter;

    public LoginInteractor(SignUpPresenter mSignUpPresenter) {
        this.mSignUpPresenter = mSignUpPresenter;
        this.mFirebaseDB = FirebaseDatabase.getInstance();
    }

    public void getUser(final String userUid) {
        final DatabaseReference dbRefUsers = mFirebaseDB.getReference(Firebase.USERS);
        dbRefUsers.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                mSignUpPresenter.setUserApp(userModel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: Handle error on presenter here.
            }
        });
    }

    public void saveUser(User user, LoginMethodModel loginMethodModel) {
/*        databaseReference.child(user.getFirstName())
                .setValue(user);
        databaseReference.child(user.getFirstName())
                .child(Firebase.USERS_LOGINS)
                .child(loginMethodModel.getLoginMethod().toString())
                .setValue(loginMethodModel);
        mSignUpPresenter.showUserCreatedSuccesfully();*/
    }

}

