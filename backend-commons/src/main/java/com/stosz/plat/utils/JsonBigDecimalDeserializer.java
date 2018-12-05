package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

@SuppressWarnings("rawtypes")
public class JsonBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonBigDecimalDeserializer.class);

    @SuppressWarnings("unchecked")
    @Override
    public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);
            String strVal = node.textValue();
            return new BigDecimal(strVal);
        } catch (Exception e) {
            LOGGER.error("JsonEnumDeserializer error.", e);
        }
        return null;
    }

}
