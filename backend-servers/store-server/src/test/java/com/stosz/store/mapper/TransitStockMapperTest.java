package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.dto.response.MatchPackRes;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.model.TransitStock;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/28]
 */
public class TransitStockMapperTest extends BaseTest {

    @Resource
    private TransitStockMapper mapper;

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void delete() throws Exception {
        int code = mapper.delete(2);
        logger.info("code:" + code);
        logger.info("ok");
    }

    @Test
    public void update() throws Exception {
        TransitStock stock = new TransitStock();
        stock.setTrackingNoOld("999999999");
        stock.setId(74);
        stock.setDeptId(2222);
        stock.setWmsId(22255);
        stock.setCreateAt(LocalDateTime.now());
        mapper.update(stock);
    }

    @Test
    public void count() throws Exception {

        TransitStock stock = new TransitStock();
        stock.setTrackingNoOldBat("11233333333,4234234234");
        int count = mapper.count(stock);
        logger.info("result:" + count);
    }

    @Test
    public void find() throws Exception {

        TransitStock stock = new TransitStock();
        stock.setTrackingNoOldBat("11233333333,4234234234");
        List<TransitStock> list = mapper.find(stock);
        logger.info("result:" + list);
    }

    @Test
    public void getTransitByTransferId() throws Exception {

        List<TransitStock> list = mapper.getTransitByTransferId(69);
        logger.info("result:" + list);
    }

}