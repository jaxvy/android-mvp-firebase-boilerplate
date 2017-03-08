package me.jaxvy.boilerplate.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.activity.presenter.BaseActivityPresenter;

/**
 * Activities only contain UI related code. The business logic lies in the Presenter classes. The
 * Presenter and Activity talk though the Callback interfaces.
 *
 * @param <T>
 */
public abstract class BaseActivity<T extends BaseActivityPresenter> extends AppCompatActivity implements
        BaseActivityPresenter.Callback {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPresenter();
        callPresenterOnCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void setupToolbar(String title) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(title);
        }
    }

    private void setupPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
    }

    protected void callPresenterOnCreate(Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.onCreate(savedInstanceState);
        }
    }

    public abstract T createPresenter();

    @Override
    public void onDestroy() {
        callPresenterOnDestroy();
        super.onDestroy();
    }

    protected void callPresenterOnDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
