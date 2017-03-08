package me.jaxvy.boilerplate.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jaxvy.boilerplate.R;
import me.jaxvy.boilerplate.ui.activity.BaseActivity;
import me.jaxvy.boilerplate.ui.fragment.presenter.BaseFragmentPresenter;


public abstract class BaseFragment<T extends BaseFragmentPresenter> extends Fragment {

    T mPresenter;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initPresenter(context);
    }

    private void initPresenter(Context context) {
        if (mPresenter == null) {
            Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                mPresenter = clazz.getDeclaredConstructor(Context.class, Fragment.class).newInstance(context, this);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onCreate(savedInstanceState, getArguments());
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.onViewCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        mPresenter.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mPresenter.onDetach();
        mPresenter = null;
        super.onDetach();
    }

    protected void setupToolbar(String title, boolean showBackArrow) {
        if (mToolbar != null) {
            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.setSupportActionBar(mToolbar);
            ActionBar actionBar = baseActivity.getSupportActionBar();
            actionBar.setTitle(title);

            actionBar.setDisplayHomeAsUpEnabled(showBackArrow);
            actionBar.setDisplayShowHomeEnabled(showBackArrow);
            mToolbar.setNavigationOnClickListener(showBackArrow ?
                    view -> baseActivity.onBackPressed() :
                    null);
        }
    }

    protected void hideKeyboard() {
        View content = getView();
        Activity activity = getActivity();
        if (content == null || activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = content.findFocus();
        if (focus != null) {
            View view = content.findFocus();
            if (view != null) {
                view.clearFocus();
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
        }
    }
}
