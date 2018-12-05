package com.stosz.plat.wms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.base.Strings;
import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.enums.ResponseResultEnum;
import com.stosz.plat.utils.HttpClient;
import com.stosz.plat.wms.model.WmsConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SendWmsUtils {

    public static final Logger logger = LoggerFactory.getLogger(SendWmsUtils.class);

    private static ObjectMapper objectMapper;

    /**
     * 将实体封装成需要的参数json串
     *
     * @param obj 参数
     * @return
     * @throws Exception
     * @date 2017年12月1日
     * @author liusl
     */
    public static String sendWmsRequest(Object obj, final String serviceMethod) throws Exception {
        WmsConfig wmsConfig = SpringContextHolder.getBean("wmsConfig");
        String baseContent = getObjectMapper().writeValueAsString(obj);
        String encrypt = wmsConfig.getENCRYPT();
        StringBuilder param = new StringBuilder("appkey=" + wmsConfig.getAPPKEY() + "&service=" + serviceMethod + "&format=" + wmsConfig.getFORMAT() + "&encrypt=" + wmsConfig.getENCRYPT());
        if (StringUtils.isNotEmpty(encrypt) && encrypt.equals("1")) {
            param.append("&content=" + Base64.encodeBase64String((baseContent.getBytes())).replace("=", "\\u003d") + "&secret=" + wmsConfig.getAPPSECRET());
        } else {
            param.append("&content=" + baseContent + "&secret=" + null);
        }
        HttpClient client = new HttpClient();
        return client.pub2(wmsConfig.getSERVICE_URL(), param.toString());
    }

    /**
     * 获取推送到wms的url和参数
     *
     * @param obj           业务对象
     * @param serviceMethod 接口url
     * @return key =推送wms的url    value=推送wms的参数
     * @throws Exception
     */
    public static Pair<String, String> prepareWmsPushParams(Object obj, final String serviceMethod) throws Exception {
        WmsConfig wmsConfig = SpringContextHolder.getBean("wmsConfig");
        String baseContent = getObjectMapper().writeValueAsString(obj);
        String encrypt = wmsConfig.getENCRYPT();
        StringBuilder param = new StringBuilder("appkey=" + wmsConfig.getAPPKEY() + "&service=" + serviceMethod + "&format=" + wmsConfig.getFORMAT() + "&encrypt=" + wmsConfig.getENCRYPT());
        if (StringUtils.isNotEmpty(encrypt) && encrypt.equals("1")) {
            param.append("&content=" + Base64.encodeBase64String((baseContent.getBytes())).replace("=", "\\u003d") + "&secret=" + wmsConfig.getAPPSECRET());
        } else {
            param.append("&content=" + baseContent + "&secret=" + null);
        }
        return  Pair.of(wmsConfig.getSERVICE_URL(), param.toString());
    }

    public static Pair<ResponseResultEnum, String> requestWms(String url, String param) {
        HttpClient client = new HttpClient();
        String responseResult = client.pub2(url, param);

        ResponseResultEnum responseResultEnum = ResponseResultEnum.fail;

        //对返回结果进行结果类型转换
        if (Strings.isNullOrEmpty(responseResult)) responseResultEnum = ResponseResultEnum.exception;

        Map map = null;
        try {
            map = objectMapper.readValue(responseResult, Map.class);
        } catch (IOException e) {
            logger.error("wms返回结果转成map错误", e);
            responseResultEnum = ResponseResultEnum.exception;
        }
        if (null != map && "true".equalsIgnoreCase(map.get("isSuccess").toString())) {
            responseResultEnum = ResponseResultEnum.success;
        }

        return Pair.of(responseResultEnum, responseResult);

    }


    public static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            objectMapper.registerModule(module);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }
}
