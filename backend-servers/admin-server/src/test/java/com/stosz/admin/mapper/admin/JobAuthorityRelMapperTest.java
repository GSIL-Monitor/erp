package com.stosz.admin.mapper.admin;

import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.test.common.JUnitBaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/11]
 */
public class JobAuthorityRelMapperTest extends JUnitBaseTest{

    public static final Logger logger = LoggerFactory.getLogger(JobAuthorityRelMapperTest.class);

    @Resource
    private JobAuthorityRelMapper jobAuthorityRelMapper;

    @Test
    public void findByUser() throws Exception {
        List<JobAuthorityRel> jobAuthorityRelList = jobAuthorityRelMapper.findByUser(989);
        boolean f = jobAuthorityRelList.stream().anyMatch(e->e.getJobAuthorityRelEnum() == JobAuthorityRelEnum.myself);
        logger.info(""+f);

    }

}