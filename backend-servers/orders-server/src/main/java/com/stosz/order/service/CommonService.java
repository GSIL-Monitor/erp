package com.stosz.order.service;


import com.stosz.plat.common.CommonException;
import com.stosz.plat.utils.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);


    public List<Map<String, Object>> getEnumJsonString(Class cls) {
        try {
            List<Map<String, Object>> result = EnumUtils.enumValueToMap(cls);
            return result;
        } catch (SecurityException | IllegalArgumentException  e) {
            logger.error("枚举转换字符串出现异常，类名：" + cls.getName(), e);
            throw new CommonException("枚举类转换字符串时异常，类名:" + cls.getName() + "异常：" + e.getMessage());
        }
    }
}
