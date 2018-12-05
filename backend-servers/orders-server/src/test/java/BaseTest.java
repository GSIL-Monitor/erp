import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.order.engine.SpringRootConfig;
import com.stosz.plat.common.MBox;
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
//        User user = new User();
//        user.setId(1);
//        user.setName("system");
//        user.setLoginid("system_id");
//        PowerMockito.mockStatic(MBox.class);
//        PowerMockito.when(MBox.getLoginUser()).thenReturn(user);
//
//        PowerMockito.when(MBox.getLoginUserId()).thenReturn(user.getId());
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
       logger.info("==============================" + desc + " 开始 类：" + (result==null?"null":result.getClass().getSimpleName()) + "  ==================================");
        String str = "";
        try {
            str = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);

        }
        logger.info(str);
        logger.info("==============================" + desc + " 结束  ==================================");
    }
}