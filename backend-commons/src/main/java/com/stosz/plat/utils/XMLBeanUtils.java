package com.stosz.plat.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shiqiangguo on 17/5/9.
 */
public final class XMLBeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(XMLBeanUtils.class);

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        /*
         * map.put("appid", WeixinUtils.APP_ID); map.put("mch_id",
         * WeixinUtils.MCH_ID);
         */
        map.put("device_info ", "WEB");
        //String nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        map.put("nonce_str", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
        map.put("body", "商品描述。XX项目");
        map.put("out_trade_no", "001t" + System.currentTimeMillis());
        map.put("total_fee", "0.01");
        map.put("trade_type", "JSAPI");
        map.put("openid", "o4lmljscgZDnjI4xthqMMnEr02fo");

        String xmlResult = "";

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : map.keySet()) {
            sb.append("<" + key + ">" + map.get(key) + "</" + key + ">");
        }
        sb.append("</xml>");
        xmlResult = sb.toString();
        logger.info(xmlResult);

    }

    public static String map2XmlString(Map<String, String> map) {
        String xmlResult = "";

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : map.keySet()) {

            String value = "<![CDATA[" + map.get(key) + "]]>";
            sb.append("<" + key + ">" + value + "</" + key + ">");
        }
        sb.append("</xml>");
        xmlResult = sb.toString();

        return xmlResult;
    }

    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map<String, String> readStringXmlOut(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            @SuppressWarnings("unchecked")
            List<Element> list = rootElt.elements();// 获取根节点下所有节点
            for (Element element : list) { // 遍历节点
                map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

}