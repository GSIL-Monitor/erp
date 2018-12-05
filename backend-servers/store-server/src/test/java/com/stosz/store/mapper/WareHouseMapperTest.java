package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.Stock;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class WareHouseMapperTest extends BaseTest {

    @Resource
    private StockMapper mapper;

    @Test
    public void insert() throws Exception {
        Stock stock = new Stock();
        stock.setId(5);
        stock.setDeptId(1);
        stock.setDeptNo("123123");
        stock.setDeptName("一营一部");
        stock.setWmsId(3);
        stock.setSku("1");
        stock.setSpu("1");
        stock.setVersion(0L);
        stock.setInstockQty(1);
        stock.setIntransitQty(1);
        stock.setUsableQty(1);
        stock.setOccupyQty(1);
        mapper.insert(stock);

    }

    @Test
    public void delete() throws Exception {
        mapper.delete(1);
        logger.info("ok");
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getById() throws Exception {
        Stock stock = mapper.getById(5);
    }

}