package com.stosz.plat.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
public  abstract  class  AbstractHandler<T>{

    protected static final Logger logger = LoggerFactory.getLogger(AbstractHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    public  boolean  handleMessage(String method, Map<String,Object> dataJsonMap)
    {
        Assert.isTrue(!Strings.isNullOrEmpty(method),"method不能为空");
//        Assert.isTrue(Arrays.asList(RabbitMQConfig.method_insert,RabbitMQConfig.method_update,RabbitMQConfig.method_delete).contains(method),"不支持的method值");
        try {
            //T dataItem = ObjectUtils.convertToObject(dataJsonMap,getTClass());
            T dataItem = objectMapper.readValue(objectMapper.writeValueAsString(dataJsonMap),getTClass());
            Assert.notNull(dataItem,"json的转换的对象为空");
            return handleMessage(method, dataItem);
        }catch (Exception ex)
        {
            logger.error(ex.getMessage(),ex);
        }
        return true;
    }



    public abstract boolean handleMessage(String method, T dataItem);

    public abstract Class<T> getTClass();

    /**
     * 子类中重写，提供一个跟消息处理器关联紧密的消息类型；
     * @return
     */
    public String  getMessageType(){
        return "";
    }



}