package io.github.javaarchive.omniverse.structures;

import java.util.List;
import java.util.Map;

public class PermissionSet {
    //  Role        Permission
    Map<String, Map<String,  Permission>> pData;
    List<String> allRoles;
}
