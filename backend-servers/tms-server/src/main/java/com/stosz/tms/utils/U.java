package com.stosz.tms.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class U {

    public static final Logger logger = LoggerFactory.getLogger(U.class);

    public static Map<String,Object> map = new HashMap<String,Object>();

    /**
     * 获得xml文件
     * @param entity
     * @param hasHead
     * @return
     * @throws JAXBException
     */
    public static String getXmlString(Object entity,Boolean hasHead) {
        StringWriter writer = new StringWriter();
        StringBuilder sb = new StringBuilder();
        try{
            JAXBContext jc = JAXBContext.newInstance(entity.getClass());
            Marshaller ma = jc.createMarshaller();
            ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            if(!hasHead){
                // 去掉生成xml的默认报文头
                ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
            }
            ma.marshal(entity, writer);
            sb.append(writer.toString());
            return sb.toString();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    public static Map parse(String soap) throws DocumentException {
        Document doc = DocumentHelper.parseText(soap);//报文转成doc对象
        Element root = doc.getRootElement();//获取根元素，准备递归解析这个XML树
        getCode(root);
        return map;
    }

    public static void getCode(Element root){
        if(root.elements()!=null){
            List<Element> list = root.elements();//如果当前跟节点有子节点，找到子节点
            for(Element e:list){//遍历每个节点
                if(e.elements().size()>0){
                    getCode(e);//当前节点不为空的话，递归遍历子节点；
                }
                if(e.elements().size()==0){
                    map.put(e.getName(), e.getTextTrim());
                }//如果为叶子节点，那么直接把名字和值放入map
            }
        }
    }
}
