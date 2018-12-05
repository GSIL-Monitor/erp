package com.stosz.order.ext.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:Excels实体属性注解
 * @author: liusl
 * @date: 2018/1/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModelProp{
    public String name();
    public int colIndex() default -1;
    public int importIndex() default -1;
    public boolean nullable() default true;
    public String interfaceXmlName() default "";
}