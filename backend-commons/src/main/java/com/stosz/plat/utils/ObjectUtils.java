/**
 * 
 */
package com.stosz.plat.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author kelvem
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ObjectUtils {

    static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    public static <T> List<T> convertToList(List<Map<?, ?>> lst, Class<T> cls) throws Exception {
        return lst.stream().map((a) -> convertToObject(a, cls)).collect(Collectors.toList());

    }

    public static <T> T convertToObject(Map<?, ?> map, Class<T> cls) {
        try {
            // SimpleModule module = new SimpleModule();
            // module.addDeserializer(Enum.class, new
            // JsonEnumRestDeserializer(cls));
            // mapper.registerModule(module);
            String str = mapper.writeValueAsString(map);
            T t2 = (T) mapper.readValue(str, cls);
            return t2;
        } catch (Exception e) {
            String errMsg = "通过map 序列化对象出现异常：" + e + "目标对象：" + cls + " 参数:" + map;
            logger.error(errMsg, e);
            throw new RuntimeException(errMsg, e);
        }

    }

    public static <K, V> Map<K, V> newMap(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    /**
     * 对于老Erp接口的返回信息，特殊处理
     * 
     * @param lst
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> convertToList2(List<Map<String, ?>> lst, Class<T> cls)
            throws Exception {
        return lst.stream().map((a) -> convertToObject2(a, cls)).collect(Collectors.toList());

    }

    public static <T> T convertToObject2(Map<String, ?> map, Class<T> cls) {
        try {
            Map newMap = new HashMap();
            for (String key : map.keySet()) {
                String newKey = StringUtils.lowerCase(key.substring(0, 1)) + key.substring(1);
                newMap.put(newKey, map.get(key));
            }
            String str = mapper.writeValueAsString(newMap);
            T t2 = (T) mapper.readValue(str, cls);
            return t2;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> toEnumName(Collection<Enum> collection) {
        return collection.stream().map(Enum::name).collect(Collectors.toList());
    }

    public static final String toString(Object objThis) {
        if (objThis == null) {
            return null;
        }
        Method[] methods = objThis.getClass().getMethods();
        // 1.将所有get开头的方法、参数数量为0、并且方法大于3个字符的对象方法过滤出来。
        // 2.将每个符合条件的方法，组合成 "属性名=属性值"的集合
        // 3.将集合用“, ”连接，前缀“类名+[”，后缀“]”
        String str = Stream.of(methods).filter(m -> m.getName().startsWith("get")
                && m.getParameterCount() == 0 && m.getName().length() > 3).map(m -> {
                    String name = StringUtils.uncapitalize(m.getName().substring(3));
                    try {
                        Optional<Object> val = Optional.ofNullable(m.invoke(objThis));
                        if (val.isPresent()) {
                            Object val_get = val.get();
                            if (val_get instanceof Collection && ((Collection) val_get).isEmpty()) {
                                return "";
                            }
                            return name + "=" + val.get() + ", ";
                        } else {
                            return "";
                        }

                    } catch (Exception e) {
                        return name + "=<Exception:" + e.getMessage() + ">";
                    }
                }).collect(Collectors.joining("", objThis.getClass().getSimpleName() + "[", "]"));
        return str;
    }

    public static final int getDefaultZero(Number n) {
        return n == null ? 0 : n.intValue();
    }
}
