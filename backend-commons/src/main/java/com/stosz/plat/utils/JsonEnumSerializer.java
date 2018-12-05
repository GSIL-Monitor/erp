package com.stosz.plat.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 枚举对象转 JSON 格式
 * </p>
 *
 * @author shiqiangguo
 * @date 2016-07-12
 */
@SuppressWarnings("rawtypes")
public class JsonEnumSerializer extends JsonSerializer<Enum> {

    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void serialize(Enum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        Map<String, Object> maps = EnumUtils.enumValueToJson(value);
        gen.writeRawValue(mapper.writeValueAsString(maps));
    }

}
