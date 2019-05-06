package com.motian.crp.utils;

import java.util.List;
import java.util.Map;

/**
 * @Author: motian
 * @Email: motian@xiyoulinux.org
 */
public class CrpWebUtils {
    public static Map<String, Object> Model(Map<String, Object> map) {
        if (map.containsKey("data")) {
            map.put("count", ((List) map.get("data")).size());
        }
        map.put("code", 0);
        return map;
    }
}
