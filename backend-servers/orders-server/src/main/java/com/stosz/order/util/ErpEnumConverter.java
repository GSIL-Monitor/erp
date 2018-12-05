package com.stosz.order.util;

import com.stosz.plat.utils.IEnum;
import org.springframework.core.convert.converter.Converter;


/**
 * @auther carter
 * create time    2017-12-11
 */
public class ErpEnumConverter implements Converter<String,IEnum> {


    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public IEnum convert(String source) {
        return null;
    }
}
