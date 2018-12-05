package com.stosz.plat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by shiqiangguo on 17/4/26.
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DateTimeFormatter formatter;

    public LocalDateConverter(String s) {
        formatter = DateTimeFormatter.ofPattern(s);
    }

    @Override
    public LocalDate convert(String source) {
        if (source == null || source.trim().length() == 0) {
            return null;
        }
        source = source.trim();
        return LocalDate.parse(source, formatter);

    }

}
