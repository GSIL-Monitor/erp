package com.stosz.plat.utils;

import com.google.common.collect.Sets;
import com.stosz.plat.common.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumUtils {

    private static final Logger logger = LoggerFactory.getLogger(EnumUtils.class);

    public static List<Map<String, Object>> getEnumJsonString(Class cls) {
        try {
            List<Map<String, Object>> result = EnumUtils.enumValueToMap(cls);
            return result;
        } catch (SecurityException | IllegalArgumentException  e) {
            throw new CommonException("枚举类转换字符串时异常，类名:" + cls.getName() + "异常：" + e.getMessage());
        }
    }

    public static Map<String, Object> enumValueToJson(Enum<?> e) {

        Map<String, Object> maps = new HashMap<>();
        HashSet<String> ignoreMethod = Sets.newHashSet("values", "valueOf", "equals", "toString",
                "hashCode", "compareTo", "compareTo", "valueOf", "getDeclaringClass", "wait",
                "getClass", "notify", "notifyAll");
        Class<?> c = e.getClass();
        Method[] methods = c.getMethods();
//        StringJoiner joiner = new StringJoiner(",", "{", "}");
        for (Method m : methods) {
            if (ignoreMethod.contains(m.getName()) || m.getParameterCount() > 0) {
                continue;
            }
            String key = m.getName();
            Object val = "";
            try {
                val = m.invoke(e);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e1) {
                val = e1.getMessage();
            }
//            String str = String.format("\"%s\":\"%s\"", key, val);
//            joiner.add(str);
            maps.put(key, val);
        }
//        return joiner.toString();
        return maps;
    }


    public static List<Map<String, Object>> enumValueToMap(Class<?> cls) {

        List<Map<String, Object>> result = new ArrayList<>();
        if (!Enum.class.isAssignableFrom(cls)) {
            Map<String, Object> map = new HashMap<>();

            map.put("name", cls.getSimpleName() + " is not a Enum Type.");
            result.add(map);
//            return "{name:" + cls.getSimpleName() + " not a Enum Type.}";
            return result;
        }

        HashSet<String> ignoreMethod = Sets.newHashSet("values", "valueOf", "equals", "toString",
                "hashCode", "compareTo", "compareTo", "valueOf", "getDeclaringClass", "wait",
                "getClass", "notify", "notifyAll");

        Set<Method> methods = Stream.of(cls.getMethods())
                .filter(e -> !ignoreMethod.contains(e.getName())
                        && e.getParameterTypes().length == 0
                        && !e.getReturnType().getName().equals("void"))
                .collect(Collectors.toSet());
        Enum<?>[] enums = new Enum<?>[0];
        try {
            enums = (Enum[]) cls.getMethod("values").invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            logger.error(e.getMessage(), e);
        }

        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (Enum e : enums) {
            Map<String, Object> map = enumValueToJson(e);
            result.add(map);
//            joiner.add(eJson);
        }

        return result;
    }


}
