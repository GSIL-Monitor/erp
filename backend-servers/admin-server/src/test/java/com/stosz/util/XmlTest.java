package com.stosz.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class XmlTest {

    public static final Logger logger = LoggerFactory.getLogger(XmlTest.class);

    @Test
    public void testXml(){


        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            //        <system id="admin" host="localhost" port="8081" name="用户中心"/>
            document = reader.read(getClass().getResourceAsStream("/erp.system.dev.xml"));
            Element root = document.getRootElement();
            List<Element> lst = root.elements("system");
            for (Element e : lst) {
                String id = e.attributeValue("id");
                String host = e.attributeValue("host");
                String port = e.attributeValue("port");
                String name = e.attributeValue("name");
                logger.info(String.format("id:%s\t host:%s\t port:%s\t name:%s" , id , host,port , name));
            }
           logger.info(""+lst);
        } catch (DocumentException e) {
            logger.error(e.getMessage(),e);
        }

    }
}
