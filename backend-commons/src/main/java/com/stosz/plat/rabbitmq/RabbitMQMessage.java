package com.stosz.plat.rabbitmq;

import com.stosz.plat.model.ITable;
import org.springframework.util.Assert;

import java.util.Optional;

public class RabbitMQMessage{

    private String messageType;
    private String method;
    private Object data;


    public RabbitMQMessage() {
    }


    public RabbitMQMessage(String messageType, String method, Object data) {
        Assert.notNull(data,"请传入非空的数据对象");
        this.messageType = messageType;
        this.method = method;
        this.data = data;

    }

    public RabbitMQMessage(String method, ITable data) {
        this(Optional.ofNullable(data).orElse(() -> "").getTable(),method,data);
    }


    public RabbitMQMessage setData(ITable data) {
        this.messageType = data.getTable();
        this.data = data;
        return this;
    }

    public String getMessageType() {
        return messageType;
    }

    public RabbitMQMessage setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RabbitMQMessage setMethod(String method) {
        this.method = method;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RabbitMQMessage setData(Object data) {
        Assert.notNull(data,"请传入非空的数据对象");
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "RabbitMQMessage{" +
                "messageType='" + messageType + '\'' +
                ", method='" + method + '\'' +
                ", data=" + data +
                '}';
    }
}
