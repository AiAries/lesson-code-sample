package com.feicui.edu.highpart.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class UrlParameterUtil {

//    "ver=1&subid=4"+"&dir=1&nid=1&stamp=20140321&cnt=20"
    public static String parameter(Map<String,String> map)
    {
        String  url = "";
        Set<Map.Entry<String, String>> entries = map.entrySet();

        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            url+=key+"="+value+"&";
        }
        //去除最后一个&符号
        return url.substring(0,url.length()-1);
    }
}
