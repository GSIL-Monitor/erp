package com.stosz.tms.utils;

import com.stosz.plat.utils.ArrayUtils;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 包含空值的转换器
 * @author feiheping
 * @version [1.0,2017年12月7日]
 */
public class DataResultNullConverter implements Converter {
	public static final Logger logger = LoggerFactory.getLogger(DataResultNullConverter.class);

	@SuppressWarnings("rawtypes")
	private Class currentType;

	private List<String> clazzNamesList;

	private HashMap<String, String> aliasMap;

	public DataResultNullConverter(List<String> clazzNamesList, HashMap<String, Class<?>> aliasMap) {
		this.clazzNamesList = clazzNamesList;
		this.aliasMap = new HashMap<>();
		if (ArrayUtils.isNotEmpty(aliasMap)) {
			for (String key : aliasMap.keySet()) {
				this.aliasMap.put(aliasMap.get(key).getName(), key);
			}
		}
		if (this.clazzNamesList == null) {
			clazzNamesList = new ArrayList<>();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		currentType = type;
		if (clazzNamesList.contains(currentType.getSimpleName())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		try {
			marshalSuper(source, writer, context, currentType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getObj(Class clazz, String nodeName, Object source) throws Exception {
		Method method = clazz.getMethod("get" + Character.toUpperCase(nodeName.substring(0, 1).toCharArray()[0]) + nodeName.substring(1));
		Object obj = null;
		obj = method.invoke(clazz.cast(source), new Object[0]);
		return obj;
	}

	@SuppressWarnings({ "rawtypes" })
	private void objConverter(Object source, HierarchicalStreamWriter writer, MarshallingContext context, Class clazz, String nodeName, Class fieldClazz) throws Exception {
		Object obj = getObj(clazz, nodeName, source);
		writer.startNode(nodeName);
		marshalSuper(obj, writer, context, fieldClazz);
		writer.endNode();
	}

	@SuppressWarnings({ "rawtypes" })
	private void collectionConverter(Object source, HierarchicalStreamWriter writer, MarshallingContext context, Class clazz, String nodeName, Field field) throws Exception {
		Type types[] = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
		Object obj = getObj(clazz, nodeName, source);
		Collection collection = null;
		if (field.getType().equals(List.class)) {
			collection = (List) obj;
		} else if (field.getType().equals(Set.class)) {
			collection = (Set) obj;
		}
		writer.startNode(nodeName);
		for (Object object : collection) {
			writer.startNode(aliasMap.get(((Class) types[0]).getName()));
			marshalSuper(object, writer, context, (Class) types[0]);
			writer.endNode();
		}
		writer.endNode();
	}

	@SuppressWarnings({ "rawtypes" })
	private void basicTypeConverter(Object source, HierarchicalStreamWriter writer, MarshallingContext context, Class clazz, String nodeName) throws Exception {
		Object obj = getObj(clazz, nodeName, source);
		writer.startNode(nodeName);
		writer.setValue(obj == null ? "" : obj.toString());
		writer.endNode();
	}

	@SuppressWarnings({ "rawtypes" })
	private void marshalSuper(Object source, HierarchicalStreamWriter writer, MarshallingContext context, Class clazz) throws Exception {
		Field fields[] = clazz.getDeclaredFields();
		for (Field field : fields) {
			String nodeName = field.getName();
			Class fieldClazz = field.getType();
			if (clazzNamesList.contains(fieldClazz.getSimpleName())) {
				objConverter(source, writer, context, clazz, nodeName, fieldClazz);
			} else if (Arrays.asList(fieldClazz.getInterfaces()).contains(Collection.class)) {
				collectionConverter(source, writer, context, clazz, nodeName, field);
			} else {
				basicTypeConverter(source, writer, context, clazz, nodeName);
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

}
