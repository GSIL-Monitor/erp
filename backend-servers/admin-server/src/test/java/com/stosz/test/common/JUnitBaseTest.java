package com.stosz.test.common;

import com.stosz.admin.engine.SpringRootConfig;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JUnit的基础类
 *
 * @author kelvem
 * @version v1.0
 * @class: JUnitBaseTest
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(classes = SpringRootConfig.class) //加载配置文件
@ActiveProfiles("local")
public abstract class JUnitBaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


}
