package com.register.me.model.data.api.model;

/**
 * Created by Jennifer - AIT on 12-02-2020AM 11:46.
 */
public class KeyValue {
    String Key;
    String Value;

    public KeyValue(String key, String value) {
        Key = key;
        Value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
