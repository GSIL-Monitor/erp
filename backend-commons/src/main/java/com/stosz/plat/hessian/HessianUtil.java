package com.stosz.plat.hessian;

import com.caucho.hessian.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @auther carter
 * create time    2017-11-29
 */
public class HessianUtil {

    private static final Logger logger  = LoggerFactory.getLogger(HessianUtil.class);


    public static HessianProxyFactoryBean getHessianProxyFactoryBean(String fullUrl,Class interfaceClass) {
        HessianProxyFactoryBean bean = HessianUtil.getHessianProxyFactoryBean();
        bean.setServiceUrl(fullUrl);
        bean.setServiceInterface(interfaceClass);
        bean.setChunkedPost(true);
        bean.setOverloadEnabled(true);
        return bean;
    }

    public  static HessianProxyFactoryBean getHessianProxyFactoryBean()
    {
        HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
        try {
            hessianProxyFactoryBean.setSerializerFactory(getSerializerFactory());
        } catch (Exception e) {
            logger.error("客户端调用时,hessian添加java.time解析器出错",e);
            throw new RuntimeException("hessian接口实例时无法获取序列化工厂！",e);
        }finally {
            return hessianProxyFactoryBean;
        }
    }

    public static HessianServiceExporter getHessianServiceExporter()
    {
        HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
        try {
            hessianServiceExporter.setSerializerFactory(getSerializerFactory());
        } catch (Exception e) {
            logger.error("服务端发布应用时,hessian添加java.time解析器出错",e);
        }finally {
            return hessianServiceExporter;
        }
    }

    /**
     * 通过反射的方式拿到ContextSerializeFactory， 给它添加新的解析器；
     * @return
     */
    public static SerializerFactory getSerializerFactory() throws Exception {

        //1,通过反射拿到 ContextSerializerFactory
        ContextSerializerFactory contextSerializerFactory = ContextSerializerFactory.create();
        Field staticSerializerMapField = contextSerializerFactory.getClass().getDeclaredField("_staticSerializerMap");
        staticSerializerMapField.setAccessible(true);
        HashMap<String, Serializer> stringSerializerHashMap = (HashMap<String, Serializer>) staticSerializerMapField.get(contextSerializerFactory);

        Field staticDeserializerMapField = contextSerializerFactory.getClass().getDeclaredField("_staticDeserializerMap");
        staticDeserializerMapField.setAccessible(true);
        HashMap<String, Deserializer> stringDeserializerHashMap = (HashMap<String, Deserializer>) staticDeserializerMapField.get(contextSerializerFactory);


        Field staticClassNameMapField = contextSerializerFactory.getClass().getDeclaredField("_staticClassNameMap");
        staticClassNameMapField.setAccessible(true);
        HashMap hashMap = (HashMap) staticClassNameMapField.get(contextSerializerFactory);

        HessianJava8TimeSerializer hessianJava8TimeSerializer = new HessianJava8TimeSerializer();
        HessianJava8TimeDeserializer hessianJava8TimeDeserializer = new HessianJava8TimeDeserializer(LocalDate.class);
        

          Arrays.asList(/*LocalDate.class,*/ LocalDateTime.class/*, LocalTime.class*/)
                  .forEach(cls->
                  {

                      stringSerializerHashMap.put(cls.getName(), hessianJava8TimeSerializer);
                      stringDeserializerHashMap.put(cls.getName(), hessianJava8TimeDeserializer);
                      hashMap.put(cls.getName(),hessianJava8TimeDeserializer);
                    });
          
        stringSerializerHashMap.put(BigDecimal.class.getName(), new StringValueSerializer());
        stringDeserializerHashMap.put(BigDecimal.class.getName(),new BigDecimalDeserializer());

        staticSerializerMapField.set(contextSerializerFactory,stringSerializerHashMap);
        staticDeserializerMapField.set(contextSerializerFactory,stringDeserializerHashMap);
        staticClassNameMapField.set(contextSerializerFactory,hashMap);

        Method initMethod = contextSerializerFactory.getClass().getDeclaredMethod("init", new Class[]{});
        initMethod.setAccessible(true);
        initMethod.invoke(contextSerializerFactory,new Object[]{});




        //2,拿到ContextSerializerFactory ;
        SerializerFactory serializerFactory = SerializerFactory.createDefault();
        Class<? extends SerializerFactory> serializerFactoryClass = serializerFactory.getClass();
        Field contextFactoryField = serializerFactoryClass.getDeclaredField("_contextFactory");
        contextFactoryField.setAccessible(true);
        //3,拿到该对象的属性对象 设置为反射修改过属性值的对象；
        contextFactoryField.set(serializerFactory,contextSerializerFactory);

        //4,再次初始化一次；
         serializerFactory = serializerFactory.createDefault();


//        Serializer serializer = contextSerializerFactory.getSerializer(LocalDate.class.getName());
//        logger.info(serializer);

        return serializerFactory;
    }


    public static void main(String[] args) {
        try {
            getSerializerFactory();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /////////////////////////

//    addSelf(LocalDate.class,"localDate",BasicSerializer.LOCAL_DATE);
//    addSelf(LocalDateTime.class,"localDateTime",BasicSerializer.LOCAL_DATE_TIME);
//    addSelf(LocalTime.class,"localTime",BasicSerializer.LOCAL_TIME);
    ////////////////////////////

//    private static void addSelf(Class cl, String typeName, int type)
//    {
//        _staticSerializerMap.put(cl.getName(), new HessianJava8TimeSerializer());
//
//        Deserializer deserializer = new HessianJava8TimeDeserializer(cl);
//        _staticDeserializerMap.put(cl.getName(), deserializer);
//        _staticClassNameMap.put(typeName, deserializer);
//    }



}
