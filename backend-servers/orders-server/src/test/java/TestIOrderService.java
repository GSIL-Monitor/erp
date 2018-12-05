import com.fasterxml.jackson.core.JsonProcessingException;
import com.stosz.order.engine.SpringRootConfig;
import com.stosz.order.ext.dto.TransportDTO;
import com.stosz.order.ext.dto.TransportRequest;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.order.mapper.order.OrdersRmaBillMapper;
import com.stosz.order.service.AfterSaleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@ContextConfiguration(classes = SpringRootConfig.class)
@ActiveProfiles("local")
public class TestIOrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrdersRmaBillMapper ordersRmaBillMapper;

    @Autowired
    private AfterSaleService afterSaleService;

    @Test
    public void testQueryOrdersByTrackingNos() throws JsonProcessingException {
        String[] trackingNos = {"625011428352","625011428374","625011428251"};
    }
    @Test
    public void testQueryOrdersItems() throws JsonProcessingException {
        String[] trackingNos = {"625011428352","625011428374","625011428251"};
    }

    @Test
    public void testTransferCancel() throws JsonProcessingException {
        /*logger.info(new ObjectMapper().writeValueAsString(orderService.transferCancel(16L, OrderEventEnum.forwardResponseOk)));*/
    }

    @Test
    public void testUpdateOrderLogisticsInfo() throws JsonProcessingException {
        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setOrderId(17);
        transportRequest.setTrackingNo("6250114282501");
        transportRequest.setShippingName("一一一一一");
/*
        logger.info(new ObjectMapper().writeValueAsString(orderService.updateOrderLogisticsInfo(transportRequest)));
*/
    }

    @Test
    public void testUpdateOrderStatus() throws JsonProcessingException {
        TransportDTO transport = new TransportDTO();
        transport.setOrderId(17);
        transport.setTrackingNo("6250114282501");
//        transport.setTrackingStatusShow("派送到达");
/*
        logger.info(new ObjectMapper().writeValueAsString(orderService.updateOrderStatus(transport)));
*/
    }

    @Test
    public void testReAssignmentLogistics() throws JsonProcessingException {
        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setOrderId(17);
        transportRequest.setTrackingNo("6250114282501");
        transportRequest.setShippingName("一一一一一");
        transportRequest.setCode("success");
/*
        logger.info(new ObjectMapper().writeValueAsString(orderService.reAssignmentLogistics(transportRequest)));
*/
    }
    @Test
    public void testStock() {
        OrdersRmaBill ordersRmaBill = ordersRmaBillMapper.getById(4);
        afterSaleService.notifyStockTakeGoods(ordersRmaBill);
    }
    @Test
    public void testAfterSaleService() {
        afterSaleService.approve(8, "拒绝", false);
    }
}