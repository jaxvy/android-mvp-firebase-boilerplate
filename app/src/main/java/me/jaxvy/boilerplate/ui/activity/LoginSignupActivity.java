package me.jaxvy.boilerplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.activity.callback.LoginSignupActivityCallback;
import me.jaxvy.boilerplate.ui.activity.presenter.LoginSignupPresenter;

public class LoginSignupActivity extends BaseActivity<LoginSignupPresenter> implements
        LoginSignupActivityCallback,
        LoginSignupPresenter.Callback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
    }

    @Override
    public LoginSignupPresenter createPresenter() {
        return new LoginSignupPresenter(this, getSupportFragmentManager());
    }

    /*
    @Override
    public void isUserSignedIn(boolean isSignedIn) {
        if (isSignedIn) {
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
    */

    @Override
    public void onSigninSuccessful() {
        finish();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
