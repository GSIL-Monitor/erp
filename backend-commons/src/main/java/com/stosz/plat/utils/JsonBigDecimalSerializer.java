package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * <p>
 * 枚举对象转 JSON 格式
 * </p>
 *
 * @author hubin
 * @date 2016-07-12
 */
@SuppressWarnings("rawtypes")
public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        BigDecimal bd = value.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        String str = bd.toString();
        gen.writeRawValue(str);
    }

}
