package me.jaxvy.boilerplate.network;

import me.jaxvy.boilerplate.model.persistence.Item;
import me.jaxvy.boilerplate.model.response.ItemCreateResponse;
import me.jaxvy.boilerplate.model.response.ItemListResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface Api {

    @POST("/users/{userId}/items.json")
    Observable<ItemCreateResponse> createItem(@Path("userId") String userId, @Body Item item);

    @GET("/users/{userId}.json")
    Observable<ItemListResponse> getItems(@Path("userId") String userId);
}
