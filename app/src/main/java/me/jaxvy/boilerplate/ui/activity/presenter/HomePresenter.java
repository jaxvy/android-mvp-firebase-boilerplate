package me.jaxvy.boilerplate.ui.activity.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import io.realm.RealmResults;
import me.jaxvy.boilerplate.model.persistence.Item;
import me.jaxvy.boilerplate.model.request.ItemListRequest;
import me.jaxvy.boilerplate.model.response.ItemListResponse;
import rx.Observable;

public class HomePresenter extends AuthenticatedPresenter<HomePresenter.Callback> {

    private RealmResults<Item> mItemList;

    public HomePresenter(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mItemList != null) {
            mItemList.removeChangeListeners();
        }
        mItemList = mRealm.where(Item.class)
                .findAllAsync();
        mItemList.addChangeListener(itemList -> mCallback.onItemsChanged(itemList));
        fetchItems();
    }

    public void fetchItems() {
        String userId = mFirebaseAuth.getCurrentUser().getUid();
        Observable<ItemListResponse> itemsCall = mApi.getItems(userId);
        mApiManager.call(new ItemListRequest(itemsCall,
                () -> Log.d("HomePresenter", "Items save to realm")));
    }

    public void signOut() {
        mRealm.beginTransaction();
        mRealm.delete(Item.class);
        mRealm.commitTransaction();

        mFirebaseAuth.signOut();
        mSharedPrefs.clearAuthToken();
        mCallback.onSignOut();
    }

    @Override
    public void onDestroy() {
        if (mItemList != null) {
            mItemList.removeChangeListeners();
        }
        super.onDestroy();
    }

    public interface Callback extends AuthenticatedPresenter.Callback {
        void onSignOut();

        void onItemsChanged(RealmResults<Item> itemList);
    }
}
