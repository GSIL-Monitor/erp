package com.stosz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.MBox;
import com.stosz.product.engine.SpringRootConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@ContextConfiguration(classes = SpringRootConfig.class)
@ActiveProfiles("local")
public class BaseTest {

    @Before
    public void setUp(){
        MBox.isTestCase=true;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Date date(String dt) {
        try {
            return sdf.parse(dt);
        } catch (ParseException e) {
            return null;
        }
    }

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected ObjectMapper mapper = new ObjectMapper();

    public void out(String desc, Object result) {
        String str = "";
        try {
            str = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
    }
}