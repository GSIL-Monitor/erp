package com.stosz.plat.common;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by shiqiangguo on 17/4/28.
 */
public class CustomerNicknameSerializer extends StdSerializer<String> {


    public CustomerNicknameSerializer() {
        this(null);
    }

    public CustomerNicknameSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(String name, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (name == null) {
            jsonGenerator.writeString("");
        }
        jsonGenerator.writeString(name);
    }
}
