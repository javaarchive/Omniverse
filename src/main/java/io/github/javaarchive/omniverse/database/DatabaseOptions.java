package io.github.javaarchive.omniverse.database;

import java.io.File;
import lombok.Getter;
import lombok.Setter;

public class DatabaseOptions {
    @Getter @Setter File dbFile;
    @Getter @Setter int cacheSize = 10*1024;
}
