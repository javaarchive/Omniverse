package io.github.javaarchive.omniverse.structures;

import com.google.gson.annotations.SerializedName;

public enum Permission {
    @SerializedName("deny")
    DENY,
    @SerializedName("allow")
    ALLOW,
    @SerializedName("none")
    NETURAL;

    static public Permission fromString(String str){
        if(str.equals("allow")){
            return Permission.ALLOW;
        }else if(str.equals("deny")){
            return Permission.DENY;
        }
        return Permission.NETURAL;
    }
}
