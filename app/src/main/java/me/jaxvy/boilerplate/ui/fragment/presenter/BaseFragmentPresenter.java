package me.jaxvy.boilerplate.ui.fragment.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.ParameterizedType;

import io.realm.Realm;
import me.jaxvy.boilerplate.utils.InjectionBase;

public class BaseFragmentPresenter<T extends BaseFragmentPresenter.Callback> extends InjectionBase {

    protected Context mContext;
    protected T mCallback;
    protected FirebaseAuth mFirebaseAuth; // Injecting FirebaseAuth with dagger causes a crash
    protected Realm mRealm;

    public BaseFragmentPresenter(Context context, Fragment fragment) {
        super(context);
        initCallback(fragment);
        mContext = context;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mRealm = Realm.getInstance(mRealmConfiguration);
    }

    private void initCallback(Fragment fragment) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mCallback = clazz.cast(fragment);
    }

    @CallSuper
    public void onCreate(Bundle savedInstanceState, Bundle arguments) {

    }

    @CallSuper
    public void onViewCreated(Bundle savedInstanceState) {

    }

    @CallSuper
    public void onDestroyView() {

    }

    @CallSuper
    public void onDestroy() {

    }

    @CallSuper
    public void onDetach() {
        if (mRealm != null) {
            mRealm.close();
        }
    }

    public interface Callback {

    }
}
