package com.stosz.purchase.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class ArrayUtils {

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        if (map == null)
            return false;
        if (map.isEmpty())
            return false;
        return true;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return !isNotEmpty(map);
    }

    public static <T> boolean isEmpty(Collection<T> list) {
        if (list == null)
            return true;
        if (list.isEmpty())
            return true;
        return false;
    }

    public static <T> boolean isNotEmpty(Collection<T> list) {
        return !isEmpty(list);
    }

    public static <T> T getEntity(Function<T, T> function) {
        return function.apply(null);
    }
    
}
