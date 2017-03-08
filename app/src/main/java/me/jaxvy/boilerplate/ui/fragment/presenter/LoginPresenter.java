package me.jaxvy.boilerplate.ui.fragment.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

import me.jaxvy.boilerplate.ui.activity.callback.LoginSignupActivityCallback;

public class LoginPresenter extends BaseFragmentPresenter<LoginPresenter.Callback> {

    private LoginSignupActivityCallback mActivityCallback;

    public LoginPresenter(Context context, Fragment fragment) {
        super(context, fragment);
        mActivityCallback = (LoginSignupActivityCallback) context;
    }

    public void signin(String email, String password, Activity activity) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        fetchAuthToken();
                    } else {
                        mCallback.onSigninFail(task.getException().getMessage());
                    }
                });
    }

    private void fetchAuthToken() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        firebaseUser.getToken(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String authToken = task.getResult().getToken();
                        mSharedPrefs.setAuthToken(authToken);
                        mActivityCallback.onSigninSuccessful();
                    } else {
                        mCallback.onSigninFail(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void onDetach() {
        mActivityCallback = null;
        super.onDetach();
    }

    public interface Callback extends BaseFragmentPresenter.Callback {

        void onSigninFail(String errorMessage);
    }
}
