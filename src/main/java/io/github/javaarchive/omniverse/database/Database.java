package io.github.javaarchive.omniverse.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class Database {
    public Gson gson = new Gson();

    public abstract String get(String key);
    public abstract void set(String key, String value);
    public void delete(String key){
        this.set(key,null);
    }

    public JsonObject get_json(String key){
        return this.gson.fromJson(this.get(key), JsonObject.class);
    }

    public void set_json(String key, JsonObject value){
        this.set(key, this.gson.toJson(value));
    }
}
