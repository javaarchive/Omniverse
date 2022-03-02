package io.github.javaarchive.omniverse.structures;

import java.util.List;
import java.util.Map;

import lombok.Getter;

public class PermissionSet {
    //         Role        Permission    Value
    public Map<String, Map<String    ,  Permission>> pData;

    @Getter private List<String> roleList;

    private transient Map<String, Integer> roleInverseIndex; // Maps role names to position

    public PermissionSet hydrate(){
        for(int i = 0; i < roleList.size(); i ++){
            this.roleInverseIndex.put(roleList.get(i), i);
        }
        return this;
    }

    public void appendRole(String str){
        
    }
}
