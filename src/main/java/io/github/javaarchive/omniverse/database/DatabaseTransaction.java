package io.github.javaarchive.omniverse.database;

public interface DatabaseTransaction {
    void run(Database db);
}
