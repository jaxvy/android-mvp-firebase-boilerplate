package me.jaxvy.boilerplate.ui.activity.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.ParameterizedType;

import io.realm.Realm;
import me.jaxvy.boilerplate.utils.InjectionBase;

/**
 * Presenters should know nothing of the UI and contain only the business logic. BaseActivityPresenter
 * also contains the initialization code.
 */
public abstract class BaseActivityPresenter<T extends BaseActivityPresenter.Callback> extends InjectionBase {

    protected T mCallback;
    protected Realm mRealm;

    public BaseActivityPresenter(Context context) {
        super(context);
        initCallback(context);
        mRealm = Realm.getInstance(mRealmConfiguration);
    }

    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        // Not mandatory for all presenters to implement
    }

    private void initCallback(Context context) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mCallback = clazz.cast(context);
    }

    @CallSuper
    public void onDestroy() {
        if (mRealm != null) {
            mRealm.close();
        }
    }

    public interface Callback {

    }
}
