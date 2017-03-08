package me.jaxvy.boilerplate.model.request;

import io.realm.Realm;
import me.jaxvy.boilerplate.model.request.listener.OnRealmSaveListener;
import me.jaxvy.boilerplate.model.request.listener.OnRequestCompleteListener;
import me.jaxvy.boilerplate.model.request.listener.OnRequestErrorListener;
import rx.Observable;

public abstract class BaseRealmRequest<T> extends BaseNetworkRequest<T> {

    private Realm mRealm;
    private OnRealmSaveListener mOnRealmSaveListener;

    public BaseRealmRequest(Observable call, OnRealmSaveListener onRealmSaveListener) {
        super(call);
        mOnRealmSaveListener = onRealmSaveListener;
    }

    public BaseRealmRequest(Observable call, OnRequestCompleteListener onRequestCompleteListener,
                            OnRealmSaveListener onRealmSaveListener) {
        super(call, onRequestCompleteListener);
        mOnRealmSaveListener = onRealmSaveListener;
    }

    public BaseRealmRequest(Observable call, OnRequestErrorListener onRequestErrorListener,
                            OnRealmSaveListener onRealmSaveListener) {
        super(call, onRequestErrorListener);
        mOnRealmSaveListener = onRealmSaveListener;
    }

    public BaseRealmRequest(Observable call, OnRequestCompleteListener onRequestCompleteListener,
                            OnRequestErrorListener onRequestErrorListener,
                            OnRealmSaveListener onRealmSaveListener) {
        super(call, onRequestCompleteListener, onRequestErrorListener);
        mOnRealmSaveListener = onRealmSaveListener;
    }

    @Override
    public void onNext(T item) {
        mRealm = Realm.getInstance(mRealmConfiguration);
        mRealm.executeTransactionAsync(realm -> {
            saveResponseToRealm(realm, item);
        }, () -> {
            if (mOnRealmSaveListener != null) {
                mOnRealmSaveListener.onRealmSaved();
            }
            if (mRealm != null && !mRealm.isClosed()) {
                mRealm.close();
            }
        });
    }

    public abstract void saveResponseToRealm(Realm realm, T t);
}
