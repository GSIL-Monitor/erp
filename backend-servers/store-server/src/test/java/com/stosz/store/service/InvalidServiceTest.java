package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.dto.request.SearchInvalidReq;
import com.stosz.store.ext.model.Invalid;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class InvalidServiceTest extends BaseTest {

    @Resource
    private InvalidService service;


    static void test() {

    }

    @Test
    public void insert() throws Exception {
        Invalid invalid = new Invalid();
        invalid.setInvalidTotal(new BigDecimal(11));
        invalid.setCalculateType(0);
        invalid.setWmsId(3);
        invalid.setInvalidType(0);
        invalid.setQty(100);
        invalid.setStockName("泰国仓");
        service.insert(invalid);

    }

    @Test
    public void query() throws Exception {
        SearchInvalidReq param = new SearchInvalidReq();
//        param.setId(1);
//        param.setCreator("张三");
//        param.setTransitId(1);
        param.setCreateAtStartTime(LocalDateTime.now());
//        param.setCreateAtEndTime(LocalDateTime.now());
      /*  RestResult resutl=service.findList(param);
        logger.info("result:"+resutl);*/

        logger.info("get user:" + ThreadLocalUtils.getUser().getLastName());
    }
}
