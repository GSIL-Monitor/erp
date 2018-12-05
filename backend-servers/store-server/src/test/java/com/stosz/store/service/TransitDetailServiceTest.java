package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.TransitDetail;
import com.stosz.store.mapper.TransitDetailMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class TransitDetailServiceTest extends BaseTest {

    @Resource
    private TransitDetailMapper mapper;


    static void test() {

    }

    @Test
    public void insert() throws Exception {
        TransitDetail bean = new TransitDetail();
        // bean.setId((int) System.currentTimeMillis());

        bean.setCreateAt(LocalDateTime.now());

        int result = mapper.insert(bean);
        logger.info("result:" + result);
    }

}
