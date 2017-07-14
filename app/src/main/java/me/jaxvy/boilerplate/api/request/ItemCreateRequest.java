package me.jaxvy.boilerplate.api.request;

import android.util.Log;

import io.reactivex.Observable;
import me.jaxvy.boilerplate.api.model.Item;
import me.jaxvy.boilerplate.api.request.listener.OnRequestCompleteListener;
import me.jaxvy.boilerplate.api.request.listener.OnRequestErrorListener;
import me.jaxvy.boilerplate.api.response.ItemCreateResponse;


public class ItemCreateRequest extends BaseNetworkRequest<ItemCreateResponse> {

    private String mTitle;
    private String mDescription;

    public ItemCreateRequest(String title, String description,
                             OnRequestCompleteListener onRequestCompleteListener,
                             OnRequestErrorListener onRequestErrorListener) {
        super(onRequestCompleteListener, onRequestErrorListener);
        mTitle = title;
        mDescription = description;
    }

    @Override
    public Observable<ItemCreateResponse> getCall() {
        return mApi.createItem(mFirebaseAuth.getCurrentUser().getUid(),
                new Item(mTitle, mDescription));
    }

    @Override
    public void onNext(ItemCreateResponse itemCreateResponse) {
        String name = itemCreateResponse.getName();
        Log.d("ItemCreateRequest", "Item id: " + name);
    }
}
