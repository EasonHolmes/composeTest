package com.ethan.netlibrary.model;


import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KeyModel {

    private final Map<String, Object> values;

    public KeyModel() {
        values = new HashMap<>();
    }

    public static KeyModel create() {
        return new KeyModel();
    }

    public static Map<String, Object> getEmpty() {
        return Collections.emptyMap();
    }

    public KeyModel of(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public Object getValues(String key) {
        return values.get(key);
    }

    public Map<String, Object> getMap() {
        return values;
    }

    @Override
    public String toString() {
        return new Gson().toJson(values);
    }
}
