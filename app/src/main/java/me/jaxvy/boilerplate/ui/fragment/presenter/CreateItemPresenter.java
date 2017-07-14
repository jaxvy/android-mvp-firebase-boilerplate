package me.jaxvy.boilerplate.ui.fragment.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import me.jaxvy.boilerplate.api.request.ItemCreateRequest;
import me.jaxvy.boilerplate.ui.activity.callback.HomeActivityCallback;

public class CreateItemPresenter extends BaseFragmentPresenter<CreateItemPresenter.Callback> {

    public HomeActivityCallback mActivityCallback;

    public CreateItemPresenter(Context context, Fragment fragment) {
        super(context, fragment);
        mActivityCallback = (HomeActivityCallback) context;
    }

    public void saveItem(String title, String description) {
        mApiManager.call(new ItemCreateRequest(title, description,
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
