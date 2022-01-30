package io.github.javaarchive.omniverse.database;

public class NamespacedDatabase extends Database{
    Database upstream;
    String namespace;

    public NamespacedDatabase(DatabaseOptions dbOpts){
        super(dbOpts);
        this.upstream = dbOpts.getUpstreamDB();
        this.namespace = dbOpts.getNamespace();
    }

    @Override
    public String get(String key) {
        return this.upstream.get(this.namespace + ":" + key);
    }

    @Override
    public void set(String key, String value) {
        this.upstream.set(this.namespace + ":" + key, value);
    }
}
