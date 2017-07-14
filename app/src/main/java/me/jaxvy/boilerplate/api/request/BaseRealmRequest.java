package me.jaxvy.boilerplate.api.request;

import io.realm.Realm;

public abstract class BaseRealmRequest<T> extends BaseNetworkRequest<T> {

    @Override
    public void onNext(T item) {
        Realm mRealm = Realm.getInstance(mRealmConfiguration);
        mRealm.executeTransactionAsync(realm -> {
                    saveResponseToRealm(realm, item);
                },
                () -> {
                    if (mRealm != null && !mRealm.isClosed()) {
                        mRealm.close();
                    }
                });
    }

    public abstract void saveResponseToRealm(Realm realm, T t);
}
