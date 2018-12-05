package com.stosz.admin.ext.service;

import com.stosz.BaseTest;
import com.stosz.admin.ext.model.JobAuthorityRel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/18]
 */
public class IJobAuthorityRelServiceTest extends BaseTest {

    public static final Logger logger = LoggerFactory.getLogger(IJobAuthorityRelServiceTest.class);
    @Resource
    private IJobAuthorityRelService jobAuthorityRelService;

    @Test
    public void getByUser() throws Exception {
        JobAuthorityRel jobAuthorityRel = jobAuthorityRelService.getByUser(599);
        logger.info(">>>>>>>>{}",jobAuthorityRel);
    }

}