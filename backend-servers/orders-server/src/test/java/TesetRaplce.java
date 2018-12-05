import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TesetRaplce {
    public static final Logger logger = LoggerFactory.getLogger(TesetRaplce.class);

    @Test
    public void testJson() {
        String  str = "http://img-test.stosz.com/productNew/2017/11/201711200218240204.jpg";
        logger.info(str.replace("http://img-test.stosz.com/",""));
    }

}
