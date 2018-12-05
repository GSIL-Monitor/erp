package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * <p>
 * Rest 接口枚举转换
 * </p>
 * 
 * @author hubin
 * @date 2016-10-21
 */
@SuppressWarnings("rawtypes")
public class JsonEnumRestDeserializer<T> extends JsonDeserializer<Enum> {

    private static final Logger logger = LoggerFactory.getLogger(JsonEnumRestDeserializer.class);

    private Class<T> enumClass;

    public JsonEnumRestDeserializer(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Enum deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        try {
            String enumName = jp.getParsingContext().getParent().getCurrentName();
            Field field = enumClass.getDeclaredField(enumName);
            Class enumClass = field.getType();
            JsonNode node = jp.getCodec().readTree(jp);
            return Enum.valueOf(enumClass, node.get("name").textValue());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
