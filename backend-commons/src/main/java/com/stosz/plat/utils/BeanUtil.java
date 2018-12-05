package com.stosz.plat.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * <p>
 * 基于CGlib，扩展BeanUtils，对于复杂类型的CGlib更有优势
 * </p>
 * 
 * @author hubin
 * @date 2016-07-14
 */
public final class BeanUtil extends BeanUtils {

    protected BeanUtil() {
        /* 保护 */
    }

    /**
     * 实例化对象
     * 
     * @param clazz
     *            类
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化对象
     * 
     * @param clazzStr
     *            类名
     * @return {T}
     */
    public static <T> T newInstance(String clazzStr) {
        try {
            Class<?> clazz = Class.forName(clazzStr);
            return newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * copy 对象属性到另一个对象，默认不使用Convert
     * 
     * @param src
     * @param clazz
     *            类名
     * @return {T}
     */
    public static <T> T clone(Object src, Class<T> clazz) {
        BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);
        T to = newInstance(clazz);
        copier.copy(src, to, null);
        return to;
    }

    /**
     * 拷贝对象
     * 
     * @param source
     *            源对象
     * @param target
     *            需要赋值的对象
     */
    public static void copy(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(),
                        targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        /*
                         * no copy null properties
                         */
                        Object value = readMethod.invoke(source);
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier
                                    .isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException(
                                "Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 将对象装成map形式 注意：生成的是unmodifiableMap
     * 
     * @param src
     *            源对象
     * @return {Map<K, V>}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(Object src) {
        return BeanMap.create(src);
    }

}
