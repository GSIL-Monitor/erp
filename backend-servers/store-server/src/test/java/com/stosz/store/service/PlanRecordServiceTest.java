package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.store.task.PlanRecordSchedule;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 19:24
 * @Update Time:
 */
public class PlanRecordServiceTest extends BaseTest {

    @Resource
    private PlanRecordSchedule planRecordSchedule;


    @Test
    public void insert() throws Exception {

        planRecordSchedule.productPlanRecord();

    }


}
