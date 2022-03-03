package io.github.javaarchive.omniverse.structures;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lombok.Getter;

public class PermissionSet {
    //         Role        Permission    Value
    public Map<String, Map<String    ,  Permission>> pData;

    @Getter private List<String> roleList;

    private transient Map<String, Integer> roleInverseIndex; // Maps role names to position

    public Permission setPerm(String role, String perm, Permission value){
        Permission oldValue = pData.get(role).get(perm);
        pData.get(role).put(perm, value);
        return oldValue;
    }

    public Permission getPerm(String role, String perm){
        return pData.get(role).get(perm);
    }

    private List<String> sortRoles(List<String> roles){
        Map<String, Integer> roleInv = roleInverseIndex;
        roles.sort(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return roleInv.get(o2) - roleInv.get(o1);
            }
        });
        return roles;
    }

    public PermissionSet hydrate(){
        this.rebuildInvIndex();
        return this;
    }

    public void appendRole(String str){
        this.roleInverseIndex.put(str,this.roleList.size());
        this.roleList.add(str);
    }

    public void rebuildInvIndex(){
        // Rebuild string to index (apply priority)
        this.roleInverseIndex.clear();
        for(int i = 0; i < roleList.size(); i ++){
            this.roleInverseIndex.put(roleList.get(i), i);
        }
    }


}
