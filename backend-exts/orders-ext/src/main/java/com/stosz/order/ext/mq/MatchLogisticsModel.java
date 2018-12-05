package com.stosz.order.ext.mq;

import com.stosz.order.ext.enums.WarehouseTypeEnum;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @auther carter
 * create time    2017-11-10
 * 物流匹配的消息实体
 */
public class MatchLogisticsModel implements Serializable {

    public static final Logger logger = LoggerFactory.getLogger(MatchLogisticsModel.class);

    public static final String  MESSAGE_TYPE = "matchLogistics";

    private Integer orderId = 0;
    private Integer warehouseId = 0;
    private WarehouseTypeEnum warehouseTypeEnum = WarehouseTypeEnum.third;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public WarehouseTypeEnum getWarehouseTypeEnum() {
        return warehouseTypeEnum;
    }

    public void setWarehouseTypeEnum(WarehouseTypeEnum warehouseTypeEnum) {
        this.warehouseTypeEnum = warehouseTypeEnum;
    }

    public static void main(String[] args) {



        MatchLogisticsModel matchLogisticsModel = new MatchLogisticsModel();
        matchLogisticsModel.setOrderId(91060);
        matchLogisticsModel.setWarehouseId(8);
        matchLogisticsModel.setWarehouseTypeEnum(WarehouseTypeEnum.normal);


        RabbitMQMessage rabbitMQMessage = new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setMessageType(MESSAGE_TYPE).setData(matchLogisticsModel);


        logger.info(JsonUtil.toJson(rabbitMQMessage));


    }


}
