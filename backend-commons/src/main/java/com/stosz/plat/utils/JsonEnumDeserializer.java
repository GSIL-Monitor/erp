package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;

@SuppressWarnings("rawtypes")
public class JsonEnumDeserializer extends JsonDeserializer<Enum> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonEnumDeserializer.class);

    @SuppressWarnings("unchecked")
    @Override
    public Enum deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        try {
            Field field = jp.getCurrentValue().getClass().getDeclaredField(jp.getCurrentName());
            Class enumClass = field.getType();
            JsonNode node = jp.getCodec().readTree(jp);
            return Enum.valueOf(enumClass, node.get("name").textValue());
        } catch (Exception e) {
            LOGGER.error("JsonEnumDeserializer error.", e);
        }
        return null;
    }

}
