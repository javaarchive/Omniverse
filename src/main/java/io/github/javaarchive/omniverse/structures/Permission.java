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

    public String toString(){
        if(this.equals(Permission.ALLOW)){
            return "allow";
        }else if(this.equals(Permission.DENY)){
            return "deny";
        }else if(this.equals(Permission.NETURAL)){
            return "netural";
        }
    }

    public String toStringCapitalized(){
        if(this.equals(Permission.ALLOW)){
            return "Allow";
        }else if(this.equals(Permission.DENY)){
            return "Deny";
        }else if(this.equals(Permission.NETURAL)){
            return "Netural";
        }
    }
}
