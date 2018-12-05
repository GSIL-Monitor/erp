package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.PlanRecord;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class PlanRecordMapperTest extends BaseTest {

    @Resource
    private PlanRecordMapper planRecordMapper;

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void delete() throws Exception {
        planRecordMapper.delete(1);
        logger.info("ok");
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }


    @Test
    public void query() throws Exception {

        PlanRecord planRecord=new PlanRecord();

        List<PlanRecord> list=planRecordMapper.query(planRecord);

        logger.info("result:"+list);
    }

}