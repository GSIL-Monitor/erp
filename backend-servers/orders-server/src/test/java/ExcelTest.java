import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.order.ext.enums.OrderQuestionStatusEnum;
import com.stosz.order.ext.enums.OrderQuestionTypeEnum;
import com.stosz.order.ext.model.OrdersQuestionDO;
import com.stosz.order.util.ExcelUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther tangtao
 * create time  2017/12/21
 */
public class ExcelTest {

    @Test
    public void export() throws FileNotFoundException {

        String[] headers = new String[]{"物流线路", "物流登记日期\n(客户反馈问题日期)", "运单号", "问题类型", "原因描述", "收件人", "收件电话", "品名", "处理结果"};
        String[] includeFields = new String[]{"logisticName", "logisticDeliveryTime", "transNo", "questionType", "questionDetail", "customerName", "customerPhone", "spuList", "dealStatusEnum"};

        List<OrdersQuestionDO> data = new ArrayList<>();
        List<OrderItemSpuDTO> spuDTOList = new ArrayList<>();

        OrderItemSpuDTO spuDTO = new OrderItemSpuDTO();
        spuDTO.setSpu("spu56464398136");
        spuDTO.setTitle("三星显示器");


        List<OrderItemSpuDTO.OrderItemSku> skuList = new ArrayList<>();

        OrderItemSpuDTO.OrderItemSku orderItemSku = new OrderItemSpuDTO.OrderItemSku();
        orderItemSku.setSku("sku56465646");
        orderItemSku.setAttr("29寸");
        orderItemSku.setQty(2);
        skuList.add(orderItemSku);

        OrderItemSpuDTO.OrderItemSku orderItemSku2 = new OrderItemSpuDTO.OrderItemSku();
        orderItemSku2.setSku("sku56465632");
        orderItemSku2.setAttr("38寸");
        orderItemSku2.setQty(1);
        skuList.add(orderItemSku2);

        spuDTO.setSkuList(skuList);

        List<OrderItemSpuDTO.OrderItemSku> skuList2 = new ArrayList<>();
        OrderItemSpuDTO spuDTO2 = new OrderItemSpuDTO();
        spuDTO2.setSpu("spu56464398134");
        spuDTO2.setTitle("罗技机械键盘");

        OrderItemSpuDTO.OrderItemSku orderItemSku3 = new OrderItemSpuDTO.OrderItemSku();
        orderItemSku3.setSku("sku56465646");
        orderItemSku3.setAttr("黑色");
        orderItemSku3.setQty(1);
        skuList2.add(orderItemSku3);

        OrderItemSpuDTO.OrderItemSku orderItemSku4 = new OrderItemSpuDTO.OrderItemSku();
        orderItemSku4.setSku("sku56465632");
        orderItemSku4.setAttr("炫彩-背光");
        orderItemSku4.setQty(1);
        skuList2.add(orderItemSku4);

        spuDTO2.setSkuList(skuList2);

        spuDTOList.add(spuDTO);
        spuDTOList.add(spuDTO2);

        for (int i = 0; i < 10; i++) {
            OrdersQuestionDO ordersQuestion = new OrdersQuestionDO();
            ordersQuestion.setLogisticName("鸿森");
            ordersQuestion.setLogisticDeliveryTime(LocalDateTime.now());
            ordersQuestion.setTransNo("YD54684684");
            ordersQuestion.setQuestionType(OrderQuestionTypeEnum.ContReach);
            ordersQuestion.setQuestionDetail("电话和地址都不对，无法联系");
            ordersQuestion.setCustomerName("收件人");
            ordersQuestion.setCustomerPhone("15868535624");
            ordersQuestion.setSpuList(spuDTOList);
            ordersQuestion.setDealStatusEnum(OrderQuestionStatusEnum.HasSendEmail);

            data.add(ordersQuestion);
        }


        FileOutputStream fileOutputStream = new FileOutputStream("D:/问题件处理导出.xls");
//        FileOutputStream fileOutputStream2 = new FileOutputStream("D:/问题件处理导出2.xls");


        ExcelUtils.exportExcel("测试", headers, includeFields, data, ExcelUtils.DATE_PATTERN, fileOutputStream);
//        ExcelUtil.exportExcel(headers, data, new String[]{}, fileOutputStream2);
    }


}
