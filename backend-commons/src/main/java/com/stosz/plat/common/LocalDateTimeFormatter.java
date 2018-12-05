package com.stosz.plat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by shiqiangguo on 17/4/26.
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DateTimeFormatter ymdFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter ymdhmsFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter ymdhmFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public LocalDateTimeFormatter() {
    }

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        if (text == null || text.trim().length() == 0) {
            return null;
        }
        text = text.trim();
        if (text.length() == 10) {
            return LocalDateTime.parse(text.trim(), ymdFormatter);
        }
        if (text.length() == 19) {
            return LocalDateTime.parse(text.trim(), ymdhmsFormatter);
        }
        if (text.length() == 16) {
            return LocalDateTime.parse(text.trim(), ymdhmFormatter);
        }
        throw new ParseException("将文本:" + text + "解析为LocalDateTime 时异常！", 0);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        if (object == null) {
            return "";
        }
        return object.format(ymdhmsFormatter);
    }
}
