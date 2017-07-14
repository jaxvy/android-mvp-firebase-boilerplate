package me.jaxvy.boilerplate.api.request;

import io.reactivex.Observable;
import io.realm.Realm;
import me.jaxvy.boilerplate.api.response.ItemListResponse;
import me.jaxvy.boilerplate.persistence.model.Item;

public class ItemListRequest extends BaseRealmRequest<ItemListResponse> {

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
                me.jaxvy.boilerplate.api.model.Item responseItem = itemListResponse.getItems().get(itemId);
                item.setTitle(responseItem.getTitle());
                item.setDescription(responseItem.getDescription());
                realm.insertOrUpdate(item);
            }
        }
    }

    @Override
    public Observable<ItemListResponse> getCall() {
        return mApi.getItems(mFirebaseAuth.getCurrentUser().getUid());
    }
}
