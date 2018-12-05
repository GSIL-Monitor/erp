package com.stosz.plat.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml操作相关方法
 * @author hongzhaoyang
 *
 */
public class XmlUtils {

    /**
     * Object to Xml
     * @param <T>
     * @throws Exception 
     */
    public static String objectToXml(Object obj) throws Exception {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    /**
     * xml to Object
     * @param xml
     * @param cls
     * @return
     * @throws Exception 
     */
    public static <T> T xmlToObject(String xml, Class<T> cls) throws Exception {
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller unMarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        T obj = (T) unMarshaller.unmarshal(reader);
        return obj;
    }

}
