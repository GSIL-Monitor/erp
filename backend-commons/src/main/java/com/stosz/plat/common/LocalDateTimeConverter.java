package com.stosz.plat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by shiqiangguo on 17/4/26.
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DateTimeFormatter ymdFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter ymdhmsFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter ymdhmFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public LocalDateTimeConverter() {
    }

    @Override
    public LocalDateTime convert(String text) {
        if (text == null || text.trim().length() == 0) {
            return null;
        }
        text = text.trim();
        if (text.length() == 10) {
            LocalDate localDate = LocalDate.parse(text, ymdFormatter);
            LocalDateTime ld = LocalDateTime.of(localDate, LocalTime.MIN);
            return ld;
        }
        if (text.length() == 19) {
            return LocalDateTime.parse(text.trim(), ymdhmsFormatter);
        }

        if (text.length() == 16) {
            return LocalDateTime.parse(text.trim(), ymdhmFormatter);
        }
        throw new RuntimeException("将文本:" + text + "解析为LocalDateTime 时异常！");

    }

}
