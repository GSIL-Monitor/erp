package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateJsonDeserializer extends  JsonDeserializer<LocalDateTime> {

    public static final Logger logger = LoggerFactory.getLogger(DateJsonDeserializer.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        try {
            LocalDateTime ldt = LocalDateTime.parse(parser.getValueAsString(),formatter);
            return  ldt;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
