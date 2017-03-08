package me.jaxvy.boilerplate.ui.fragment.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import me.jaxvy.boilerplate.model.persistence.Item;
import me.jaxvy.boilerplate.model.request.ItemCreateRequest;
import me.jaxvy.boilerplate.model.response.ItemCreateResponse;
import me.jaxvy.boilerplate.ui.activity.callback.HomeActivityCallback;
import rx.Observable;

public class CreateItemPresenter extends BaseFragmentPresenter<CreateItemPresenter.Callback> {

    public HomeActivityCallback mActivityCallback;

    public CreateItemPresenter(Context context, Fragment fragment) {
        super(context, fragment);
        mActivityCallback = (HomeActivityCallback) context;
    }

    public void saveItem(String title, String description) {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        String userId = mFirebaseAuth.getCurrentUser().getUid();

        Observable<ItemCreateResponse> itemCall = mApi.createItem(userId, item);
        mApiManager.call(new ItemCreateRequest(itemCall,
                () -> mActivityCallback.closeCreateItemFragment(),
                throwable -> Log.e("CreateItemPresenter", "onError", throwable)
        ));
    }

    @Override
    public void onDetach() {
        mActivityCallback = null;
        super.onDetach();
    }

    public interface Callback extends BaseFragmentPresenter.Callback {

    }
}
