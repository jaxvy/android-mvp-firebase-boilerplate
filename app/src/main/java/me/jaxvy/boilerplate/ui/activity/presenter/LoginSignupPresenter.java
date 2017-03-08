package me.jaxvy.boilerplate.ui.activity.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.fragment.LoginFragment;

public class LoginSignupPresenter extends BaseActivityPresenter<LoginSignupPresenter.Callback> {

    private FragmentManager mFragmentManager;

    public LoginSignupPresenter(Context context, FragmentManager fragmentManager) {
        super(context);
        mFragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.LoginSignupActivity_content, LoginFragment.newInstance(), LoginFragment.TAG);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public interface Callback extends BaseActivityPresenter.Callback {

    }
}
