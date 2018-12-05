package com.stosz.purchase.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author feiheping
 * @version [1.0,2017年11月10日]
 */
@SuppressWarnings("all")
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static final String INCLUDE = "JSON_INCLUDE";
	private static final String EXCEPT = "JSON_EXCEPT";

	@com.fasterxml.jackson.annotation.JsonFilter(INCLUDE)
	interface MyJsonInclude {
	}

	@com.fasterxml.jackson.annotation.JsonFilter(EXCEPT)
	interface MyJsonExcept {
	}

	public static String toJson(Object dest) {
		return toJson(dest, "yyyy-MM-dd HH:mm:ss");
	}

	public static String toJson(Object dest, String dateFormat) {
		if (dest == null) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		if (StringUtils.hasText(dateFormat)) {
			objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
		}
		try {
			String value = objectMapper.writeValueAsString(dest);
			return value;
		} catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
		return null;
	}

	public static <T> T jsonToObject(String jsonContent, Class<T> classT) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			T object = objectMapper.readValue(jsonContent, classT);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
		return null;
	}

	public static <K, V> HashMap<K, V> jsonToMap(String jsonContent, Class<?> classT) {
		if (StringUtils.isEmpty(jsonContent)) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			HashMap<K, V> object = (HashMap<K, V>) objectMapper.readValue(jsonContent, classT);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
		return null;
	}

	public static <K, V> LinkedHashMap<K, V> jsonToLinkedMap(String jsonContent, Class<?> classT) {
		if (StringUtils.isEmpty(jsonContent)) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			LinkedHashMap<K, V> object = (LinkedHashMap<K, V>) objectMapper.readValue(jsonContent, classT);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
		return null;
	}

	public static String toJsonInclude(Object dest, String... includeFields) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		try {
			if (includeFields != null && includeFields.length > 0) {
				objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(includeFields)));
				objectMapper.addMixIn(dest.getClass(), MyJsonInclude.class);
			}

			return objectMapper.writeValueAsString(dest);
		} catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
		return null;
	}

	public static String toJsonExclude(Object dest, String... excludeFields) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		try {
			if (excludeFields != null && excludeFields.length > 0) {
				objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCEPT, SimpleBeanPropertyFilter.serializeAllExcept(excludeFields)));

				objectMapper.addMixIn(dest.getClass(), MyJsonExcept.class);
			}

			return objectMapper.writeValueAsString(dest);
		} catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
		return null;
	}

}
