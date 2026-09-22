package com.ruoyi.system.outto.core;

import java.util.*;
import java.util.Map.Entry;

public class MapSort {
    /**
     *
     * @Title: sortMap
     * @Description: 对集合内的数据按key的字母顺序做排序
     */
    public List<Map.Entry<String, Object>> sortMap(final Map<String, Object> map) {
        final List<Map.Entry<String, Object>> infos = new ArrayList<Map.Entry<String, Object>>(map.entrySet());

        // 重写集合的排序方法：按字母顺序
        Collections.sort(infos, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(final Entry<String, Object> o1, final Entry<String, Object> o2) {
                return (o1.getKey().toString().compareTo(o2.getKey()));
            }
        });

        return infos;
    }
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        //利用匿名内部类，重写compare to 方法

        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }


}
class MapKeyComparator implements Comparator<String> {


    @Override
    public int compare(String str1, String str2) {

        return str1.compareTo(str2);
    }
}
