package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.dto.request.SearchTakeStockReq;
import com.stosz.store.ext.model.TakeStock;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/28]
 */
public class TakeStockMapperTest extends BaseTest {

    @Resource
    private TakeStockMapper mapper;

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
    }

    @Test
    public void count() throws Exception {

        TakeStock stock = new TakeStock();

        logger.info("result:");
    }


    @Test
    public void getTransitIdList() throws Exception {

    }

    @Test
    public void getList() throws Exception {
        SearchTakeStockReq param = new SearchTakeStockReq();

        List<TakeStock> lst = mapper.find(param);
        logger.info("result:" + lst);
    }

}