package com.stosz.purchase.utils;

import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Field;


/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/29 16:48
 */
public class IBeanUtil {

    private static final String key = "";
    @Resource
    private Environment environment;

    public static boolean checkObjFieldIsNotNull(Object obj) {
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(obj) == null) {
                    Assert.isTrue(false, f.getName() + "不能为空");
                }
            }
        } catch (IllegalAccessException e) {

        }
        return true;
    }

  /*  public void payStateNotice(PayStateNotice payStateNotice) throws Exception {

        String property = environment.getProperty(payStateNotice.getBillType().concat(key));
        Object object = SpringContextHolder.getBean(property);
        Method payStateMethod = ClassUtils.getMethod(object.getClass(), "payStateNotice", PayStateNotice.class);
        payStateMethod.invoke(object, payStateNotice);

    }*/
}
