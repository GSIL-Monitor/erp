import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.mapper.order.OrdersAdditionMapper;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.outsystem.wms.WmsOrderService;
import com.stosz.plat.utils.HttpClient;
import com.stosz.plat.wms.bean.OrderCancelRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by liusl on 2017/12/12.
 */
public class TestWmsInterface extends  BaseTest {

    public static final Logger logger = LoggerFactory.getLogger(TestWmsInterface.class);
    @Autowired
    private WmsOrderService wmsOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersAdditionMapper ordersAdditionMapper;
    @Autowired
    private OrdersItemsMapper ordersItemsMapper;
    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    @Test
    public void testSubCreateSaleOrder(){
        Orders orders = orderService.findOrderById(1);
        orders.setMerchantOrderNo("1004922");
        OrdersAddition ordersAddition = ordersAdditionMapper.getByOrderId(1);
        List<OrdersItem> ordersItems = ordersItemsMapper.getByOrderId(1);
        OrdersLink ordersLink = ordersLinkMapper.getByOrderId(1);
        wmsOrderService.subCreateSaleOrder(orders, ordersAddition, ordersItems, ordersLink);
    }
    @Test
    public void subCancelOrder(){
        OrderCancelRequest orderCancelRequest = new  OrderCancelRequest();
        orderCancelRequest.setOrderId(String.valueOf(1));
        orderCancelRequest.setOrderType("XTRK");
        orderCancelRequest.setGoodsOwner("");
        logger.info("调用仓储WMS取消订单接口 请求参数 request-->", orderCancelRequest.toString());
    }
    @Test
    public void subOrderWmsOut(){
        String result = new HttpClient().pub2("http://localhost:8083/wms/api/orderWmsOut?key=123456&result_data={\"shippingId\":\"33\",\"counts\":\"10\"}", null);
        Assert.hasText(result,"未知原因导致返回结果为空，请与wms系统相关人员确认");
        logger.info("result:----"+result+"------------------");
    }
   /* @Test
    public  void testWms() throws IOException {
        String baseContent= "{\"headList\":[{\"orderCode\":\"LB20170503000083\",\"bity\":\"EBUS\",\"warehouseCode\":\"01\",\"platformCode\":\"0\",\"platformName\":\"淘宝网站\",\n" +
                "\"downDate\":\"2016-12-0712:21:45\",\"payTime\":\"2016-12-07 11:21:45\",\"auditTime\":\"2016-12-08 10:21:45\",\n" +
                "\"isDeliveryPay\":\"0\",\"consignee\":\"LB\",\"provinceName\":\"广东\",\"cityName\":\"深圳\",\"areaName\":\"龙岗\",\n" +
                "\"address\":\"街道办\",\n" +
                "\"mobile\":\"13911111\",\"amountReceivable\":\"12\",\"actualPayment\":\"33\",\"detailList\":[{\"sku\":\"2016426002\",\"qty\":\"7\"},{\"sku\":\"2016426005\",\"qty\":\"7\"}]}]}";
        String param = "appkey=app_wms&service=subCreateSaleOrder?format=JSON&encrypt=0&content="+baseContent+"&secret="+null;
        String result = new HttpClient().pub2("http://192.168.105.128:8885/Gwms_bgn_test/httpService", param);
        Assert.hasText(result,"未知原因导致返回结果为空，请与wms系统相关人员确认");
        logger.info("result:----"+result+"------------------");
    }*/
    public static  void main(String[] args){
        String baseContent= "{\"headList\":[{\"orderCode\":\"601030567\",\"bity\":\"EBUS\",\"warehouseCode\":\"Y\",\"fromCode\":\"\",\"platformCode\":\"2\",\"platformName\":\"布谷鸟\",\"downDate\":\"2018-1-8 02:04:10\",\"auditTime\":\"2018-01-17 14:41:55\",\"logisticsCompanyCode\":\"42\",\"logisticsCompanyName\":\"BJT_name\",\"expressNo\":\"BJT00007\",\"isDeliveryPay\":\"true\",\"shopName\":\"布谷鸟\",\"consignee\":\"王康康\",\"provinceName\":\"香港\",\"cityName\":\"香港\",\"areaName\":\"旺角\",\"address\":\"香港 中華民國宜蘭縣礁溪鄉開蘭路170之22號\",\"mobile\":\"0933771550\",\"goodsOwner\":\"136\",\"buyerMessage\":\"AAA\",\"orderPrice\":\"1988.00\",\"amountReceivable\":\"1988.00\",\"actualPayment\":\"100\",\"detailList\":[{\"sku\":\"1061434\",\"qty\":\"1\",\"price\":\"1988.00\"}]}]}";
        String param = "appkey=app_wms&service=subCreateSaleOrder&format=JSON&encrypt=0&content="+baseContent+"&secret="+null;
        String result = new HttpClient().pub2("http://192.168.105.128:8882/GwallServices_test/httpService", param);
        Assert.hasText(result,"未知原因导致返回结果为空，请与wms系统相关人员确认");
        logger.info("result:----"+result+"------------------");
    }
}
