package com.register.me.model;

import android.util.Log;

import com.google.gson.JsonObject;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:19.
 */
public class JsonBuilder {

    public JsonObject getUserSignUpJson(String email,String password, String role){
        JsonObject object = new JsonObject();
        object.addProperty("email",email);
        object.addProperty("password",password);
        object.addProperty("role",role);
        return object;
    }

    public JsonObject getAvatarJson(String encodedValue){
        JsonObject object = new JsonObject();
        object.addProperty("data",encodedValue);
        return object;
    }
}
