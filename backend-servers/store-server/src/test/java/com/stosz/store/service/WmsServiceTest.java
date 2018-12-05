package com.stosz.store.service;

import com.google.common.collect.Lists;
import com.stosz.BaseTest;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.mapper.WmsMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class WmsServiceTest extends BaseTest {

    @Resource
    private WmsMapper wmsMapper;


    static void test() {

    }

    @Test
    public void insert() throws Exception {
        Wms wms = new Wms();
        wms.setAddress("湖北省武汉市");
        wms.setLinkman("张三");
        wms.setMobile("1325656485");
        wms.setName("武汉仓");
        wms.setCreatorId(1);
        wms.setPriority(1);
        wms.setState(0);
        wms.setZoneId(1);
        wms.setZoneName("湖北");
        wms.setCreator("1");
        wms.setType(1);
        wms.setWmsSysCode("gga234");
        wms.setSender("布谷鸟");
        wms.setSenderCity("深圳市");
        wms.setSenderCountry("中国");
        wms.setSenderEmail("bgn@stosz.com.cn");
        wms.setSenderProvince("广东省");
        wms.setSenderTown("前海");
        wms.setSenderZipcode("519000");
        wms.setUseWms(0);
        int i = wmsMapper.insert(wms);
        logger.info("" + i);
    }

    @Test
    public void query() throws Exception {

        logger.info("result:" + wmsMapper.query(new Wms()));
    }

    @Test
    public void count() throws Exception {

        logger.info("result:" + wmsMapper.count(new Wms()));
    }

    @Test
    public void insertBat() throws Exception {
        List<Wms> wmsList = Lists.newArrayList();
        for (int i = 0; i < 5; ++i) {
            Wms wms = new Wms();
            wms.setAddress("湖北省武汉市");
            wms.setLinkman("张三");
            wms.setMobile("1325656485");
            wms.setName("武汉仓"+i);
            wms.setCreatorId(1);
            wms.setPriority(1);
            wms.setState(0);
            wms.setZoneId(1);
            wms.setZoneName("湖北");
            wms.setCreator("1");
            wms.setType(1);
            wms.setWmsSysCode("gga234");
            wms.setSender("布谷鸟");
            wms.setSenderCity("深圳市");
            wms.setSenderCountry("中国");
            wms.setSenderEmail("bgn@stosz.com.cn");
            wms.setSenderProvince("广东省");
            wms.setSenderTown("前海");
            wms.setSenderZipcode("519000");
            wms.setUseWms(0);

            wmsList.add(wms);
        }

        logger.info("result:" + wmsMapper.insertBatch(wmsList));
    }
}
