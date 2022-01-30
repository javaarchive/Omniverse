package io.github.javaarchive.omniverse.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Database {
    public Gson gson = new Gson();
    public DatabaseOptions dbOpts;

    public Database(DatabaseOptions dbOptsToSet){
        this.dbOpts = dbOptsToSet;
    }

    private final AtomicBoolean locked = new AtomicBoolean(false);

    public abstract String get(String key);
    public abstract void set(String key, String value);
    public void delete(String key){
        this.set(key,null);
    }
    public boolean contains(String key){
        try{
            String val = this.get(key);
            if(val == null){
                return false;
            }
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public JsonObject get_json(String key){
        return this.gson.fromJson(this.get(key), JsonObject.class);
    }

    public <T> T get_obj(String key, Class<T> cls){
        return this.gson.fromJson(this.get(key), cls);
    }

    public void set_json(String key, JsonObject value){
        this.set(key, this.gson.toJson(value));
    }

    public void set_json_from_obj(String key, Object value){
        this.set(key, this.gson.toJson(value));
    }

    public void lock(){
        if(this.locked.getAndSet(true)){
            this.waitSync();
        }else{
            // let's go we have the lock!
        }
    }

    public void unlock(){
        // may cause race conditons
        // TODO: Write better solution or use a lock from java
        this.locked.getAndSet(false);
        this.notifySync();
    }

    public synchronized void waitSync() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            e.printStackTrace();
        }
    }

    public synchronized void notifySync(){
        this.notifyAll();
    }

    public void transaction(DatabaseTransaction trans){
        this.lock();
        trans.run(this);
        this.unlock();
    }

    public void cleanup(){
        // nothing for now
    }
}
