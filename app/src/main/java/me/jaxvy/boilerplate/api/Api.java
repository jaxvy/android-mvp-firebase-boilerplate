package me.jaxvy.boilerplate.api;

import io.reactivex.Observable;
import me.jaxvy.boilerplate.api.model.Item;
import me.jaxvy.boilerplate.api.response.ItemCreateResponse;
import me.jaxvy.boilerplate.api.response.ItemListResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @POST("/users/{userId}/items.json")
    Observable<ItemCreateResponse> createItem(@Path("userId") String userId, @Body Item item);

    @GET("/users/{userId}.json")
    Observable<ItemListResponse> getItems(@Path("userId") String userId);
}
