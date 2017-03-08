package me.jaxvy.boilerplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import me.jaxvy.boilerplate.ui.activity.presenter.AuthenticatedPresenter;

public abstract class AuthenticatedActivity<T extends AuthenticatedPresenter> extends BaseActivity<T>
        implements AuthenticatedPresenter.Callback {

    /**
     * Activities extending from AuthenticatedActivity can call their presenter's onCreate() method
     * only if they're signed in. If not signed in we logout the user
     * @param savedInstanceState
     */
    @Override
    protected void callPresenterOnCreate(Bundle savedInstanceState) {
        if (mPresenter != null && mPresenter.isUserSignedIn()) {
            mPresenter.onCreate(savedInstanceState);
        } else {
            finish();
            Intent intent = new Intent(this, LoginSignupActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void callPresenterOnDestroy() {
        if (mPresenter != null && mPresenter.isUserSignedIn()) {
            mPresenter.onDestroy();
        }
    }
}
