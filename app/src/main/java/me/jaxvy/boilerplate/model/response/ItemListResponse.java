package me.jaxvy.boilerplate.model.response;

import java.util.Map;

import me.jaxvy.boilerplate.model.persistence.Item;

public class ItemListResponse {

    private Map<String, Item> items;

    public Map<String, Item> getItems() {
        return items;
    }
}
