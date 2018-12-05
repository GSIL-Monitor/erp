package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.order.ext.dto.TransitItemDTO;
import com.stosz.order.ext.dto.TransitStockDTO;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.common.RestResult;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.TransitStockMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TransitStockServiceTest extends BaseTest {

    @Resource
    private TransitStockMapper mapper;

    @Autowired
    private TransitStockService service;

    @Autowired
    private IOrderService orderService;

    static void test() {

    }

    @Test
    public void insert() throws Exception {
        TransitStock transitStock = new TransitStock();
        transitStock.setSku("sku11122333333");
        transitStock.setDeptId(1);
        transitStock.setSku("sku");
        transitStock.setOrderIdOld(8888L);
        transitStock.setTrackingNoOld("1213333");
        transitStock.setState(TransitStateEnum.wait_inTransit.name());
        transitStock.setWmsId(1021);
        transitStock.setStockName("美国仓");
        transitStock.setCreateAt(LocalDateTime.now());

        int result = mapper.insert(transitStock);
        logger.info("result:" + result);
    }

    @Test
    public void findList() throws Exception {
        TransitStock transitStock = new TransitStock();
        // transitStock.setId((int) System.currentTimeMillis());
        transitStock.setSku("sku11122333333");
        transitStock.setDeptId(1);
        transitStock.setCreateAt(LocalDateTime.now());

        RestResult result = service.findList(transitStock);

        logger.info("result:" + result);
    }

    @Test
    public void update() throws Exception {
        TransitStock transitStock = new TransitStock();
        transitStock.setId(1);
        transitStock.setSku("sku11122333333");
        transitStock.setDeptId(1);
        transitStock.setCreateAt(LocalDateTime.now());

        int result = mapper.update(transitStock);
        //失败-0
        logger.info("result:" + result);
    }

    @Test
    public void findByTrackNo() throws Exception {

        String trackNo = "11233333333";
        TransitStock transitStock = service.findByTrackNoOld(trackNo);

        logger.info("result:" + transitStock);
    }

    @Test
    public void rpc() throws Exception {

        List<String> stocks = new ArrayList<>();
        stocks.add("625011428251");
        stocks.add("625011428352");
        stocks.add("625011428374");
        stocks.add("625011428262");
        stocks.add("625011428236");
        stocks.add("625011428273");
        stocks.add("625011428284");//
        /*List<TransitStockDTO> list = orderService.queryOrdersByTrackingNos(stocks);
        logger.info("result:" + JsonUtils.toJson(list));*/
    }

    @Test
    public void rma2stock() {


        TransitStockDTO dto = new TransitStockDTO();

        List<TransitItemDTO> transitItemList = new ArrayList<>();

        TransitItemDTO dto1 = new TransitItemDTO();
        dto1.setSpu("spu0000");
        dto1.setSku("sku8888");
        dto1.setQty(2);
        dto1.setDeptId(33);
        dto1.setTrackingNoOld("123");
        dto1.setTotalAmount(new BigDecimal(100));
        transitItemList.add(dto1);

        dto.setTransitItemList(transitItemList);

        dto.setDeptId(33);
        dto.setDeptName("test Dept");
        dto.setRmaId(1);
        dto.setTrackingNoOld("123");
        dto.setWmsId(12);
        dto.setStockName("测试仓库");
        dto.setSku("6789789");

        service.save(dto);
        logger.info("result:");
    }
}

