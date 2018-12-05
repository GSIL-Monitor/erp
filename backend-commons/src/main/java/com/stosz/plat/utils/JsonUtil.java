package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * JackJson 简单工具类
 * </p>
 * 
 * @author hubin
 * @date 2016-12-05
 */
public class JsonUtil {

    // 记录日志
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * <p>
     * 对象转换为字符串
     * </p>
     * 
     * @param value
     *            转换对象
     * @return
     */
    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("[ {} ] toJson error {}", value, e.getMessage());
        }
        return null;
    }

    /**
     * <p>
     * JSON 字符串转换为对象
     * </p>
     * 
     * @param content
     *            JSON 字符串
     * @param valueType
     *            转换对象类型
     * @return
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            if (StringUtils.isNotEmpty(content)) {
                // 指定策略不存在 json 字段不处理反射实体
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(content, valueType);
            }
        } catch (Exception e) {
            logger.error("[ {} ] readValue error {}", valueType, e.getMessage());
        }
        return null;
    }

}
