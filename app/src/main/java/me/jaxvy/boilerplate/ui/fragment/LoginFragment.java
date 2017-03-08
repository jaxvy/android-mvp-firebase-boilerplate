package me.jaxvy.boilerplate.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.fragment.presenter.LoginPresenter;

public class LoginFragment extends BaseFragment<LoginPresenter> implements
        LoginPresenter.Callback {

    public static final String TAG = LoginFragment.class.getName();

    @BindView(R.id.LoginFragment_email)
    TextInputEditText mEmailTextInputEditText;

    @BindView(R.id.LoginFragment_password)
    TextInputEditText mPasswordTextInputEditText;

    @BindView(R.id.LoginFragment_signin)
    Button mSignInButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_login, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setupToolbar("Login", false);
    }

    @OnClick(R.id.LoginFragment_signin)
    public void onClickSigninButton() {
        String email = mEmailTextInputEditText.getText().toString();
        String password = mPasswordTextInputEditText.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            mSignInButton.setEnabled(false);
            mSignInButton.setText("Logging in...");
            mPresenter.signin(email, password, getActivity());
        }
    }

    @Override
    public void onSigninFail(String errorMessage) {
        mSignInButton.setEnabled(true);
        mSignInButton.setText("Login");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Login error");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("Try again", null);
        builder.show();
    }
}
