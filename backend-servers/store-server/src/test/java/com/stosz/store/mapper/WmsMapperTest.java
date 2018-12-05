package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.Wms;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class WmsMapperTest extends BaseTest {

    @Resource
    private WmsMapper mapper;

    @Test
    public void insert() throws Exception {

        for (int i = 0; i < 1000; i++) {
            Wms wms = new Wms();
            wms.setAddress("湖北省丹江口市幸福家园");
            wms.setLinkman("TransferEventEnum");
            wms.setMobile("1325656485");
            wms.setName("大哥仓" + i);
            wms.setCreatorId(1);
            wms.setPriority(1);
            wms.setState(0);
            wms.setZoneId(1);
            wms.setZoneName("湖北");
            wms.setCreator("1");
            wms.setType(1);
            wms.setUseWms(0);
            wms.setWmsSysCode("gga234");
            mapper.insert(wms);
        }
    }

    @Test
    public void delete() throws Exception {
        mapper.delete(13);
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void changeState() throws Exception {
        Wms wms = new Wms();
        wms.setId(8);
        wms.setState(1);
        int result = mapper.changeState(wms);

        logger.info("{}",result);
    }

    @Test
    public void query() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        logger.info("{}",now);
        for (int i = 22; i < 1022; i++) {
            mapper.getById(i);
        }
        LocalDateTime now1 = LocalDateTime.now();
        logger.info("{}",now1);


    }

    @Test
    public void query3() throws Exception {
        Integer[] ids = new Integer[1009];
        for (Integer i = 22; i < 1023; i++) {
            for (Integer j = 0; j < 1000; j++)
                ids[j] = i;
        }
        LocalDateTime now3 = LocalDateTime.now();
        logger.info("{}",now3);
        mapper.findByIds(Arrays.asList(ids));
        LocalDateTime now4 = LocalDateTime.now();
        logger.info("{}",now4);

    }

    @Test
    public void query1() throws Exception {
        int[] ids =new int[200];
        for (int i = 22; i < 122; i++) {
            ids[i] = i;
        }
        logger.info("{}",ids.length);
        LocalDateTime now = LocalDateTime.now();
        logger.info("{}",now);
        // mapper.findByIds(ids);
        LocalDateTime now1 = LocalDateTime.now();
        logger.info("{}",now1);
    }

    @Test
    public void findRpc() throws Exception {
        Integer[] ids={1,2,15,16};
        List<Wms> list=mapper.findByIds(Arrays.asList(ids));
        logger.info("11:"+list);

    }


}