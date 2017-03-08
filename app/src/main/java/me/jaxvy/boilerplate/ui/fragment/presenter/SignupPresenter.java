package me.jaxvy.boilerplate.ui.fragment.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

public class SignupPresenter extends BaseFragmentPresenter<SignupPresenter.Callback> {

    public SignupPresenter(Context context, Fragment fragment) {
        super(context, fragment);
    }

    public interface Callback extends BaseFragmentPresenter.Callback {

    }
}
