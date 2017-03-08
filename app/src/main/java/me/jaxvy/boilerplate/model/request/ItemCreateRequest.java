package me.jaxvy.boilerplate.model.request;

import android.util.Log;

import me.jaxvy.boilerplate.model.request.listener.OnRequestCompleteListener;
import me.jaxvy.boilerplate.model.request.listener.OnRequestErrorListener;
import me.jaxvy.boilerplate.model.response.ItemCreateResponse;
import rx.Observable;

public class ItemCreateRequest extends BaseNetworkRequest<ItemCreateResponse> {

    public ItemCreateRequest(Observable<ItemCreateResponse> call, OnRequestCompleteListener onRequestCompleteListener, OnRequestErrorListener onRequestErrorListener) {
        super(call, onRequestCompleteListener, onRequestErrorListener);
    }

    @Override
    public void onNext(ItemCreateResponse itemCreateResponse) {
        String name = itemCreateResponse.getName();
        Log.d("ItemCreateRequest", "Item id: " + name);
    }
}
