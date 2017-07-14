package me.jaxvy.boilerplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.activity.callback.LoginSignupActivityCallback;
import me.jaxvy.boilerplate.ui.activity.presenter.DefaultActivityPresenter;
import me.jaxvy.boilerplate.ui.fragment.LoginFragment;

public class LoginSignupActivity extends BaseActivity<DefaultActivityPresenter> implements
        DefaultActivityPresenter.Callback,
        LoginSignupActivityCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        if (savedInstanceState == null) {
            showLoginFragment();
        }
    }

    private void showLoginFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.LoginSignupActivity_content, LoginFragment.newInstance(), LoginFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public DefaultActivityPresenter createPresenter() {
        return new DefaultActivityPresenter(this);
    }

    @Override
    public void onSigninSuccessful() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
