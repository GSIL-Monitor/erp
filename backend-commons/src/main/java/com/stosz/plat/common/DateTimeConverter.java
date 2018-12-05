package com.stosz.plat.common;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeConverter implements Converter<String, Date> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<SimpleDateFormat> formatters = Lists.newArrayList();

    public DateTimeConverter(List<String> patterns) {
        patterns.forEach(e -> {
            final SimpleDateFormat dateFormat = new SimpleDateFormat(e, Locale.CHINA);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            formatters.add(dateFormat);
        });
    }


    @Override
    public Date convert(String source) {
        if (source == null || source.trim().length() == 0) {
            return null;
        }
        source = source.trim();

        for (SimpleDateFormat df:formatters) {
            try {
                final Date date = df.parse(source);
                if (date != null)
                    return date;
            } catch (ParseException e) {
                logger.info("将字符串转换成日期类型时异常！输入:{} 异常描述:{}", source, e.getMessage());
            }
        }
        return null;

    }

}
