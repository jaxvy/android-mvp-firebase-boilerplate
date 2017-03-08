package me.jaxvy.boilerplate.model.request;

import io.realm.Realm;
import me.jaxvy.boilerplate.model.persistence.Item;
import me.jaxvy.boilerplate.model.request.listener.OnRealmSaveListener;
import me.jaxvy.boilerplate.model.response.ItemListResponse;
import rx.Observable;

public class ItemListRequest extends BaseRealmRequest<ItemListResponse> {
    public ItemListRequest(Observable<ItemListResponse> call, OnRealmSaveListener onRealmSaveListener) {
        super(call, onRealmSaveListener);
    }

    @Override
    public void saveResponseToRealm(Realm realm, ItemListResponse itemListResponse) {
        if (itemListResponse != null && itemListResponse.getItems() != null) {
            for (String itemId : itemListResponse.getItems().keySet()) {
                Item item = realm.where(Item.class)
                        .equalTo("id", itemId)
                        .findFirst();
                if (item == null) {
                    item = realm.createObject(Item.class, itemId);
                }
                Item responseItem = itemListResponse.getItems().get(itemId);
                item.setTitle(responseItem.getTitle());
                item.setDescription(responseItem.getDescription());
                realm.insertOrUpdate(item);
            }
        }
    }
}
