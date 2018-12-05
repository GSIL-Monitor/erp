package com.stosz.plat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shiqiangguo on 17/4/26.
 */
public class DateConverter implements Converter<String, Date> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat formatter;

    public DateConverter(String s) {
        formatter = new SimpleDateFormat(s);
    }

    @Override
    public Date convert(String source) {
        if (source == null || source.trim().length() == 0) {
            return null;
        }
        source = source.trim();
        try {
            return formatter.parse(source);
        } catch (ParseException e) {
            logger.info("将字符串转换成日期类型时异常！输入:{} 异常描述:{}", source, e.getMessage());
            return null;
        }


    }

}
