package com.stosz.order.sync.mq.consumer;

import com.stosz.order.ext.dto.TransportDTO;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.order.service.OrderServiceImpl;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @auther liushilei
 * create time    2018-1-15
 */
@Component
public class LogisticsTrajectoryHandler extends AbstractHandler<TransportDTO> {

    public static final Logger logger = LoggerFactory.getLogger(LogisticsTrajectoryHandler.class);

    @Autowired
    private IOrderService orderService;

    /**
     * 业务说明： 修改订单状态（拒收 签收...） 物流轨迹抓取到物流状态（如果是特殊节点的状态，需要更新订单状态）  add by liushilei
     *
     * @param dataItem
     * @return 按数据库成功返回1，失败0
     */
    @Override
    public boolean handleMessage(String method, TransportDTO dataItem) {
        orderService.updateOrderStatus(dataItem);
        return true;
    }


    @Override
    public Class<TransportDTO> getTClass() {
        return TransportDTO.class;
    }

}
