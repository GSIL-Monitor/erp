package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.TransitItem;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/28]
 */
public class TransitItemMapperTest extends BaseTest {

    @Resource
    private TransitItemMapper mapper;

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void delete() throws Exception {
        int code = mapper.delete(2);
        logger.info("code:"+code);
        logger.info("ok");
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void count() throws Exception {

        TransitItem stock = new TransitItem();

    }

    @Test
    public void find() throws Exception {

        TransitItem stock = new TransitItem();
    }

    @Test
    public void getSkusByTransitId() throws Exception {

        List<TransitItem> list = mapper.getSkusByTransitId(58);
        logger.info("result:" + list);
    }

}