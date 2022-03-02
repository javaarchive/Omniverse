package io.github.javaarchive.omniverse.structures;

import com.google.gson.annotations.SerializedName;

public enum Permission {
    @SerializedName("deny")
    DENY,
    @SerializedName("allow")
    ALLOW,
    @SerializedName("none")
    NETURAL;
}
