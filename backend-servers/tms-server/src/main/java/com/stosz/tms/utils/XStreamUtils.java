package com.stosz.tms.utils;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.model.api.IndonesiaContent;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

public class XStreamUtils {

	public static final Logger logger = LoggerFactory.getLogger(XStreamUtils.class);

	/**
	 * 不是复杂对象 不用嵌套
	 * @param root
	 * @param object
	 * @return
	 */
	public static <T> String toXml(String root, T object) {
		Assert.notNull(object, "转换的对象不能为空");
		// XmlFriendlyNameCoder 解决对象属性为下划线的问题
		XStream stream = new XStream(new DomDriver(null, new XmlFriendlyNameCoder("_-", "_")));
		stream.alias(root, object.getClass());
		String contentXml = stream.toXML(object);
		return contentXml;
	}

	/**
	 * 包含空值的节点
	 * @param root
	 * @param object
	 * @return
	 */
	public static <T> String toXmlIncludeNull(String root, T object) {
		Assert.notNull(object, "转换的对象不能为空");
		XStream stream = new XStream();
		stream.alias(root, object.getClass());
		stream.registerConverter(new DataResultNullConverter(Arrays.asList(object.getClass().getSimpleName()), null));
		String contentXml = stream.toXML(object);
		return contentXml;
	}

	/**
	 * 复杂对象 多级对象嵌套，需要配置aliasMap
	 * aliasMap.put("order", ShylonContent.class);
	 * @param object
	 * @param aliasMap
	 * @param includeNull
	 * @return
	 */
	public static <T> String toXml(T object, HashMap<String, Class<?>> aliasMap, boolean includeNull) {
		Assert.notNull(object, "转换的对象不能为空");
		XStream stream = new XStream(new DomDriver(null, new XmlFriendlyNameCoder("_-", "_")));
		if (ArrayUtils.isNotEmpty(aliasMap)) {
			List<String> clazzNamesList = new ArrayList<>();
			for (String key : aliasMap.keySet()) {
				stream.alias(key, aliasMap.get(key));
				clazzNamesList.add(aliasMap.get(key).getSimpleName());
			}
			if (includeNull) {
				DataResultNullConverter resultNullConverter = new DataResultNullConverter(clazzNamesList, aliasMap);
				stream.registerConverter(resultNullConverter);
			}
		}
		String contentXml = stream.toXML(object);
		return contentXml;
	}

	/**
	 * 使用Jaxb方式将对象转成XML
	 * @param object
	 * @param isIncludeNull 是否包含空值
	 * @return
	 * @throws JAXBException
	 */
	public static <T> String jaxbToXml(T object, boolean isIncludeNull) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = context.createMarshaller();
		if (isIncludeNull) {
			marshaller.setListener(new NullMarshallerListener());
		}
		StringWriter writer = new StringWriter();
		marshaller.marshal(object, writer);
		return writer.toString();
	}
	
	public static <T> String jaxbToXml(T object) throws JAXBException {
		return jaxbToXml(object, false);
	}
	
	
	
	

	/**
	 * 将XML转为HashMap
	 * @param xml
	 * @param element
	 * @return
	 */
	public static Parameter<String, Object> xmlToMap(String xml) {
		return xmlToMap(xml, null);
	}

	/**
	 * 将XML转为HashMap
	 * @param xml
	 * @param element
	 * @return
	 */
	public static Parameter<String, Object> xmlToMap(String xml, Element element) {
		Parameter<String, Object> parameter = new Parameter<>();
		if (element == null) {
			SAXReader saxReader = new SAXReader();
			try {
				Document document = saxReader.read(new StringReader(xml));
				Element rootElement = document.getRootElement();
				Iterator<Element> iterator = rootElement.elementIterator();
				while (iterator.hasNext()) {
					Element childElement = iterator.next();
					if (childElement.elements().size() > 0) {
						Parameter<String, Object> childElements = xmlToMap(xml, childElement);
						parameter.put(childElement.getName(), childElements);
					} else {
						parameter.put(childElement.getName(), childElement.getStringValue());
					}
				}
			} catch (DocumentException e) {
				logger.error(e.getMessage(),e);
			}
		} else {
			for (Object elementObject : element.elements()) {
				if (elementObject instanceof Element) {
					Element childElement = (Element) elementObject;
					if (childElement.elements().size() > 0) {
						LinkedHashMap<String, Object> childElements = xmlToMap(xml, childElement);
						parameter.put(childElement.getName(), childElements);
					} else {
						parameter.put(childElement.getName(), childElement.getStringValue());
					}
				}
			}
		}
		return parameter;
	}

	public static void main(String[] args) {
		// String xml =
		// "<wlb><is_success>T</is_success><jobno>0000000000000</jobno><temp><hello>12112</hello></temp></wlb>";
		// LinkedHashMap<String, Object> LinkedHashMap = xmlToMap(xml, null);
		// logger.info(LinkedHashMap);

		IndonesiaContent content = new IndonesiaContent();
		content.setCCTax("1212");
		logger.info(XStreamUtils.toXml("request", content));
	}
}
