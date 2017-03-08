package me.jaxvy.boilerplate.ui.activity.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticatedPresenter<T extends AuthenticatedPresenter.Callback> extends BaseActivityPresenter<T> {

    protected FirebaseAuth mFirebaseAuth; // Injecting FirebaseAuth with dagger causes a crash

    public AuthenticatedPresenter(Context context) {
        super(context);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isUserSignedIn() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        boolean isUserSignedIn = firebaseAuth != null &&
                firebaseAuth.getCurrentUser() != null;
        return isUserSignedIn;
    }

    public interface Callback extends BaseActivityPresenter.Callback {

    }
}
