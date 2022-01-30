package io.github.javaarchive.omniverse.database;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.IOException;

import static org.iq80.leveldb.impl.Iq80DBFactory.*;

public class LevelDatabase extends Database{
    Options levelOpts;
    DB levelDB;

    public LevelDatabase(DatabaseOptions dbOptsToSet) {
        super(dbOptsToSet);
        this.levelOpts = new Options();
        this.levelOpts.createIfMissing(true);
        this.levelOpts.compressionType(CompressionType.NONE); // TODO: allow customize
        try {
            this.levelDB = factory.open(this.dbOpts.getDbFile(), this.levelOpts);
        }catch(IOException iex){
            iex.printStackTrace();
        }
    }

    @Override
    public String get(String key) {
        return asString(this.levelDB.get(bytes(key)));
    }

    @Override
    public void set(String key, String value) {
        this.levelDB.put(bytes(key), bytes(value));
    }

    @Override
    public void delete(String key) {
        this.levelDB.delete(bytes(key));
    }

    // default contains implementation should be ok


    @Override
    public void cleanup() {
        super.cleanup();
        try {
            this.levelDB.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
