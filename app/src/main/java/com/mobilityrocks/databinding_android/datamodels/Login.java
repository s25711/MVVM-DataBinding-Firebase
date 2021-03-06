package com.mobilityrocks.databinding_android.datamodels;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobilityrocks.databinding_android.Utilities.Utils;
import com.mobilityrocks.databinding_android.intefaces.LoginCallbacks;

import org.w3c.dom.Text;

public class Login extends BaseObservable {

    String email;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyChange();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyChange();
    }

    public boolean isEmailEmpty(){
        return TextUtils.isEmpty(getEmail());
    }

    public boolean isPaswordEmpty(){
        return TextUtils.isEmpty(getPassword());
    }


    public void firebaseLoginResponse( final LoginCallbacks loginCallbacks, Activity activity){

        if (Utils.getInstance().isNetworkConnected(activity)) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            Utils.getInstance().pDialogShow(activity);

            firebaseAuth.signInWithEmailAndPassword(getEmail(), getPassword())
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                           Utils.getInstance().pDilaogHide();

                            if (task.isSuccessful()) {

                                loginCallbacks.onLoginSucess("Login Success");

                            } else {

                                loginCallbacks.onLoginFailure(task.getException().getMessage());

                            }
                        }
                    });

        }else{

            loginCallbacks.onNetworkConnectionFailure(false);

        }

    }

}
