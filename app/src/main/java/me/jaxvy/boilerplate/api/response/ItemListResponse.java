package me.jaxvy.boilerplate.api.response;

import java.util.Map;

import me.jaxvy.boilerplate.api.model.Item;

public class ItemListResponse {

    private Map<String, Item> items;

    public Map<String, Item> getItems() {
        return items;
    }
}
