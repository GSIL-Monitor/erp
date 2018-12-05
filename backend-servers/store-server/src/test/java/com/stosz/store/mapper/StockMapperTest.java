package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.Stock;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/10]
 */
public class StockMapperTest extends BaseTest {

    @Resource
    private StockMapper mapper;


    @Test
    public void queryStock() throws Exception {
        Stock stock=new Stock();
        stock.setWmsId(8);
       List<Stock> list= mapper.queryStock(stock);
       logger.info("{}",list);
    }


}